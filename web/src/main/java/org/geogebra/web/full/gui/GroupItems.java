package org.geogebra.web.full.gui;

import java.util.ArrayList;

import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.groups.Group;
import org.geogebra.common.main.App;
import org.geogebra.common.main.Localization;
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
		addGroupItemIfNeeded(popup, app);
		addUngroupItemIfNeeded(popup, app);
	}

	private void addUngroupItemIfNeeded(GPopupMenuW popup, App app) {
		if (!app.getSelectionManager().getSelectedGroups().isEmpty()) {
			popup.addItem(createUngroupItem(app));
		}
	}

	private AriaMenuItem createUngroupItem(final App app) {
		return new AriaMenuItem(app.getLocalization().getMenu("Ungroup"), false,
				new Scheduler.ScheduledCommand() {
					@Override
					public void execute() {
						removeSelectedGroup(app);
					}
				});
	}

	private void removeSelectedGroup(App app) {
		for (Group group : app.getSelectionManager().getSelectedGroups()) {
			app.getKernel().getConstruction().removeGroup(group);
		}
	}

	private void addGroupItemIfNeeded(GPopupMenuW popup, App app) {
		if (geos.size() < 2) {
			return;
		}
		popup.addItem(createGroupItem(app));
	}

	private AriaMenuItem createGroupItem(final App app) {
		return new AriaMenuItem(app.getLocalization().getMenu("Group"), false,
				new Scheduler.ScheduledCommand() {
			@Override
			public void execute() {
				createGroup(app);
			}
		});
	}

	private void createGroup(App app) {
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
