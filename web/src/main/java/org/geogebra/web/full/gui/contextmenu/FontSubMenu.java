package org.geogebra.web.full.gui.contextmenu;

import java.util.List;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import org.geogebra.common.euclidian.text.InlineTextController;
import org.geogebra.web.html5.gui.laf.FontFamily;
import org.geogebra.web.html5.gui.util.AriaMenuBar;
import org.geogebra.web.html5.gui.util.AriaMenuItem;
import org.geogebra.web.html5.main.AppW;

import com.google.gwt.user.client.ui.Widget;

/**
 * Submenu for Font item in 3-dot menu of inline text
 *
 * @author Laszlo
 *
 */
public class FontSubMenu extends AriaMenuBar {

	private final List<FontFamily> fonts;
	private InlineTextController textController;
	private final AppW app;

	/**
	 * @param app the application
	 * @param textController to format text.
	 */
	public FontSubMenu(AppW app, InlineTextController textController) {
		this.app = app;
		this.fonts = app.getVendorSettings().getTextToolFonts();
		this.textController = textController;

		createItems();
		addStyleName("notesContextSubMenu");
	}

	private void createItems() {
		for (final FontFamily font : fonts) {
			ScheduledCommand command = new ScheduledCommand() {
				@Override
				public void execute() {
					textController.format("font", font.cssName());
					app.storeUndoInfo();
				}
			};

			AriaMenuItem item = new AriaMenuItem(font.displayName(), false, command);
			item.getElement().setClassName("listMenuItem");
			addItem(item);
		}
	}

	@Override
	public void stylePopup(Widget widget) {
		selectCurrent();
	}

	private void selectCurrent() {
		String font = textController.getFormat("font", "");
		for (FontFamily family : fonts) {
			if (font.equals(family.cssName())) {
				selectItem(fonts.indexOf(family));
				return;
			}
		}
		unselect();
	}

}
