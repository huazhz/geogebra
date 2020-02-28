package org.geogebra.common.euclidian;

import java.util.ArrayList;

import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.main.App;

/**
 * Manager class for groups
 *
 * @author laszlo
 */
public class GroupManager {
	private final Construction construction;
	private App app;

	/**
	 * Constructor
	 *
	 * @param app The application.
	 */
	public GroupManager(App app) {
		this.app = app;
		this.construction = app.getKernel().getConstruction();
	}

	/**
	 * Creates a group from geoList;
	 *
	 * @param geos to group.
	 */
	public void createGroup(ArrayList<GeoElement> geos) {
		construction.createGroup(geos);
		unfixAll(geos);
		app.storeUndoInfo();
	}

	private void unfixAll(ArrayList<GeoElement> geos) {
		for (GeoElement geo: geos) {
			geo.setFixed(false);
		}
	}
}
