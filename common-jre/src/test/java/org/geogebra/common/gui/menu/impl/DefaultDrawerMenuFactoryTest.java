package org.geogebra.common.gui.menu.impl;

import org.geogebra.common.GeoGebraConstants;
import org.geogebra.common.gui.menu.DrawerMenu;
import org.geogebra.common.gui.menu.DrawerMenuFactory;
import org.geogebra.common.gui.menu.MenuItem;
import org.geogebra.common.gui.menu.MenuItemGroup;
import org.geogebra.common.move.ggtapi.models.AuthenticationModel;
import org.geogebra.common.move.ggtapi.operations.LogInOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mockito;

import java.util.List;

public class DefaultDrawerMenuFactoryTest {

	private LogInOperation logInOperation;

	@Before
	public void setUp() {
		AuthenticationModel baseModel = Mockito.mock(
				AuthenticationModel.class, Answers.RETURNS_MOCKS);
		logInOperation = Mockito.mock(LogInOperation.class, Answers.CALLS_REAL_METHODS);
		logInOperation.setModel(baseModel);
	}

	@Test
	public void testGraphingWebLoggedOut() {
		Mockito.when(logInOperation.isLoggedIn()).thenReturn(false);
		DefaultDrawerMenuFactory factory = new DefaultDrawerMenuFactory(
				GeoGebraConstants.Platform.WEB,
				GeoGebraConstants.Version.GRAPHING, logInOperation);
		assertBasicProperties(factory, 4, 7, 5, 2, 1);
	}

	@Test
	public void testGraphingWebLoggedIn() {
		Mockito.when(logInOperation.isLoggedIn()).thenReturn(true);
		DefaultDrawerMenuFactory factory = new DefaultDrawerMenuFactory(
				GeoGebraConstants.Platform.WEB,
				GeoGebraConstants.Version.GRAPHING, logInOperation);
		assertBasicProperties(factory, 4, 7, 5, 2, 2);
	}

	@Test
	public void testScientificIosExam() {
		DefaultDrawerMenuFactory factory = new DefaultDrawerMenuFactory(
				GeoGebraConstants.Platform.IOS,
				GeoGebraConstants.Version.SCIENTIFIC, null, true);
		assertBasicProperties(factory, 2, 2, 3);
	}

	@Test
	public void testGraphingIosExam() {
		DefaultDrawerMenuFactory factory = new DefaultDrawerMenuFactory(
				GeoGebraConstants.Platform.IOS,
				GeoGebraConstants.Version.GRAPHING, null, true);
		assertBasicProperties(factory, 2, 5, 3);
	}

	@Test
	public void testGraphingAndroidLoggedOutExam() {
		Mockito.when(logInOperation.isLoggedIn()).thenReturn(false);
		DrawerMenuFactory factory = new DefaultDrawerMenuFactory(
				GeoGebraConstants.Platform.ANDROID,
				GeoGebraConstants.Version.GRAPHING, logInOperation, true);
		assertBasicProperties(factory, 3, 6, 3, 1);
	}

	@Test
	public void testScientificWeb() {
		DrawerMenuFactory factory = new DefaultDrawerMenuFactory(
				GeoGebraConstants.Platform.WEB,
				GeoGebraConstants.Version.SCIENTIFIC, null);
		assertBasicProperties(factory, 3, 1, 5, 2);
	}

	private void assertBasicProperties(DrawerMenuFactory factory, int numberOfGroups,
									   int... subgroupItemCounts) {
		DrawerMenu menu = factory.createDrawerMenu();
		Assert.assertNotNull(menu.getTitle());
		List<MenuItemGroup> groups = menu.getMenuItemGroups();
		Assert.assertEquals(numberOfGroups, groups.size());
		for (int i = 0; i < subgroupItemCounts.length; i++) {
			MenuItemGroup group = groups.get(i);
			List<MenuItem> menuItems = group.getMenuItems();
			Assert.assertEquals(subgroupItemCounts[i], menuItems.size());
			for (MenuItem menuItem: menuItems) {
				Assert.assertNotNull(menuItem.getIcon());
				Assert.assertNotNull(menuItem.getLabel());
			}
		}
	}
}
