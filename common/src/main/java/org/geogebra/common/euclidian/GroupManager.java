package org.geogebra.common.euclidian;

import java.util.ArrayList;

import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.groups.Group;
import org.geogebra.common.main.App;

/**
 * Manager class for groups
 *
 * @author laszlo
 */
public class GroupManager {
	private final Construction construction;

	/**
	 * Constructor
	 *
	 * @param app The application.
	 */
	public GroupManager(App app) {
		this.construction = app.getKernel().getConstruction();
	}

	/**
	 * Creates a group from geoList;
	 *
	 * @param geos to group.
	 */
	public void createGroup(ArrayList<GeoElement> geos) {
		construction.createGroup(geos);
	}

	/**
	 * Gets all geos in a given group
	 *
	 * @param group to retrieve geos from.
	 * @return geos of the group
	 */
	public ArrayList<GeoElement> getGeosOf(Group group) {
		return group.getGroupedGeos();
	}
}
