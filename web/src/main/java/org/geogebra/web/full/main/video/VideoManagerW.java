package org.geogebra.web.full.main.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.geogebra.common.awt.MyImage;
import org.geogebra.common.euclidian.draw.DrawVideo;
import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.geos.GeoVideo;
import org.geogebra.common.main.App;
import org.geogebra.common.media.MediaURLParser;
import org.geogebra.common.media.VideoManager;
import org.geogebra.common.media.VideoURL;
import org.geogebra.common.util.AsyncOperation;
import org.geogebra.web.full.gui.layout.DockPanelW;
import org.geogebra.web.full.gui.layout.panels.EuclidianDockPanelW;
import org.geogebra.web.full.main.AppWFull;
import org.geogebra.web.html5.css.GuiResourcesSimple;
import org.geogebra.web.html5.main.MyImageW;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Class for managing audio content.
 *
 * @author laszlo
 *
 */
public class VideoManagerW implements VideoManager {

	private AppWFull app;

	/**
	 * true if only preview images are needed (i.e. for image export)
	 */
	private boolean previewOnly = false;

	private Map<DrawVideo, AbstractVideoPlayer> players = new HashMap<>();
	private ArrayList<AbstractVideoPlayer> cache = new ArrayList<>();

	public VideoManagerW(AppWFull app) {
		this.app = app;
	}

	@Override
	public void loadGeoVideo(DrawVideo geo) {
		addPlayer(geo);
		updatePlayer(geo);
	}

	@Override
	public void play(DrawVideo video) {
		if (video == null) {
			return;
		}

		playerOf(video).setBackground(false);
		app.getMaskWidgets().masksToForeground();
	}

	@Override
	public void createPreview(GeoVideo geo, final AsyncOperation<MyImage> cb) {
		final Image img = new Image();
		img.getElement().setAttribute("crossorigin", "anonymous");
		img.setUrl(geo.getPreviewUrl());
		img.addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				final MyImage prev = new MyImageW(
						ImageElement.as(img.getElement()), false);
				cb.callback(prev);
				RootPanel.get().remove(img);
			}
		});
		img.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				img.setUrl(GuiResourcesSimple.INSTANCE.mow_video_player()
						.getSafeUri());
			}
		});
		RootPanel.get().add(img);
	}

	@Override
	public void setPreviewOnly(boolean preview) {
		previewOnly = preview;
	}

	@Override
	public boolean isPreviewOnly() {
		return previewOnly;
	}

	@Override
	public void addPlayer(final DrawVideo video) {
		// use int instead of iterator to prevent concurrent access
		for (int i = 0; i < cache.size(); i++) {
			AbstractVideoPlayer other = cache.get(i);
			if (other.matches(video.getVideo())) {
				players.put(video, other);
				other.video = video;
				other.asWidget().setVisible(true);
				cache.remove(other);
				return;
			}
		}

		final AbstractVideoPlayer player = !isOnline()
				? createPlayerOffline(video, players.size())
				: createPlayer(video, players.size()) ;

		addPlayerToFrame(video, player);
	}

	private void addPlayerToFrame(DrawVideo video, AbstractVideoPlayer player) {
		if (player == null) {
			return;
		}

		players.put(video, player);

		DockPanelW panel = app.getGuiManager().getLayout().getDockManager()
				.getPanel(App.VIEW_EUCLIDIAN);
		((EuclidianDockPanelW) panel).getEuclidianPanel().add(player);
	}

	private AbstractVideoPlayer createPlayerOffline(DrawVideo video, int id) {
		return new VideoOffline(video, id);
	}

	private AbstractVideoPlayer createPlayer(DrawVideo video, int id) {
		switch (video.getVideo().getFormat()) {
		case VIDEO_YOUTUBE:
			return new YouTubePlayer(video, id);
		case VIDEO_HTML5:
			return new HTML5Player(video, id);
		case VIDEO_MEBIS:
			return new MebisPlayer(video, id);
		case AUDIO_HTML5:
		case NONE:
		default:
			return null;
		}
	}

	@Override
	public void removePlayer(final DrawVideo video) {
		if (!hasPlayer(video)) {
			return;
		}
		playerOf(video).asWidget().removeFromParent();
		players.remove(video);
	}

	@Override
	public boolean hasPlayer(DrawVideo video) {
		return players.containsKey(video);
	}

	private AbstractVideoPlayer playerOf(DrawVideo video) {
		return players.get(video);
	}

	@Override
	public void updatePlayer(DrawVideo video) {
		if (!hasPlayer(video) || !video.getVideo().hasChanged()) {
			return;
		}
		playerOf(video).update();
	}

	@Override
	public void removePlayers() {
		App app = null;
		for (AbstractVideoPlayer player : players.values()) {
			player.asWidget().removeFromParent();
			if (app == null) {
				app = player.getVideo().getKernel().getApplication();
			}
		}

		players.clear();
		if (app != null) {
			app.getActiveEuclidianView().getEuclidianController().clearSelectionAndRectangle();
		}
	}

	@Override
	public void storeVideos() {
		for (AbstractVideoPlayer player : players.values()) {
			player.asWidget().setVisible(false);
			cache.add(player);
		}
		players.clear();
	}

	public boolean isOnline() {
		return app.getNetworkOperation().isOnline();
	}

	@Override
	public void backgroundAll() {
		for (AbstractVideoPlayer player : players.values()) {
			player.setBackground(true);
		}
	}

	@Override
	public GeoVideo createVideo(Construction c, VideoURL videoURL) {
		return new GeoVideo(c, videoURL.getUrl(), videoURL.getFormat());
	}

	@Override
	public String getYouTubeId(String url) {
		return MediaURLParser.getYouTubeId(url);
	}

	@Override
	public String getMebisId(String url) {
		return MediaURLParser.getMebisId(url);
	}

	@Override
	public void clearStoredVideos() {
		for (AbstractVideoPlayer player : cache) {
			player.asWidget().removeFromParent();
		}
		cache.clear();
	}

	@Override
	public void onError(DrawVideo video) {
		removePlayer(video);
		AbstractVideoPlayer offlinePlayer = createPlayerOffline(video, players.size() + 1);
		addPlayerToFrame(video, offlinePlayer);
	}

	@Override
	public boolean isPlayerOffline(DrawVideo video) {
		return playerOf(video).isOffline();
	}

	public Element getElement(DrawVideo drawable) {
		return playerOf(drawable).asWidget().getElement();
	}
}
