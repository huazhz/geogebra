package org.geogebra.web.full.gui;

import java.util.ArrayList;

import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.groups.Group;
import org.geogebra.common.main.App;
import org.geogebra.web.full.javax.swing.GPopupMenuW;
import org.geogebra.web.html5.gui.util.AriaMenuItem;

import com.google.gwt.core.client.Scheduler;

/**
 * Class to create group related menu items.
 */
public class GroupItems {
	private ArrayList<GeoElement> geos;

	/**
	 * Constructor for adding Group/Ungroup menu items
	 */
	GroupItems(App app) {
		this.geos = app.getSelectionManager().getSelectedGeos();
	}

	/**
	 * Add items that are available to currently selected geos.
	 * @param popup the menu to add items to.
	 */
	void addAvailableItems(GPopupMenuW popup, App app) {
		addGroupItem(popup, app);
		addUngroupItem(popup, app);
	}

	private void addGroupItem(GPopupMenuW popup, App app) {
		if (geos.size() >= 2 && hasGeoNotInGroup(app)) {
			popup.addItem(createGroupItem(app));
		}
	}

	private boolean hasGeoNotInGroup(App app) {
		for (GeoElement geo : geos) {
			if (geo.getParentGroup() == null) {
				return true;
			}
		}
		return app.getSelectionManager().getSelectedGroups().size() > 1 ? true : false;
	}

	private void addUngroupItem(GPopupMenuW popup, App app) {
		if (!app.getSelectionManager().getSelectedGroups().isEmpty()) {
			popup.addItem(createUngroupItem(app));
		}
	}

	private AriaMenuItem createUngroupItem(final App app) {
		return new AriaMenuItem(app.getLocalization().getMenu("ContextMenu.Ungroup"), false,
				new Scheduler.ScheduledCommand() {
					@Override
					public void execute() {
						ungroupGroups(app);
						app.storeUndoInfo();
					}
				});
	}

	private void ungroupGroups(App app) {
		for (GeoElement geo : geos) {
			Group groupOfGeo = geo.getParentGroup();
			if (groupOfGeo != null) {
				app.getKernel().getConstruction().removeGroupFromGroupList(groupOfGeo);
				app.getSelectionManager().removeGroupeFromSelectedGroups(groupOfGeo);
				geo.setParentGroup(null);
			}
		}
	}

	private AriaMenuItem createGroupItem(final App app) {
		return new AriaMenuItem(app.getLocalization().getMenu("ContextMenu.Group"), false,
				new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				createGroup(app);
			}
		});
	}

	private void createGroup(App app) {
		ungroupGroups(app);
		app.getKernel().getConstruction().createGroup(geos);
		unfixAll();
		app.storeUndoInfo();
	}

	private void unfixAll() {
		for (GeoElement geo: geos) {
			geo.setFixed(false);
		}
	}
}
