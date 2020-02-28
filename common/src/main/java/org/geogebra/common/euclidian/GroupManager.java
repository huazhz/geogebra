package org.geogebra.common.euclidian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	public List<GeoElement> getGeosOf(Group group) {
		if (group == null) {
			return Collections.emptyList();
		}

		List<GeoElement> geos = new ArrayList<>();
		for (GeoElement geo: construction.getGeoSetConstructionOrder()) {
			if (geo.getParentGroup() == group) {
				geos.add(geo);
			}
		}
		return geos;
	}
}
