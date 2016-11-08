/* 
GeoGebra - Dynamic Mathematics for Everyone
http://www.geogebra.org

This file is part of GeoGebra.

This program is free software; you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by 
the Free Software Foundation.

 */

/*
 * MyError.java
 *
 * Created on 04. Oktober 2001, 09:29
 */

package org.geogebra.common.main;

/**
 *
 * @author Markus
 * 
 */
public class MyParseError extends MyError {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates new MyError
	 * 
	 * @param app
	 *            application
	 * @param errorName
	 *            error name (should be a key in error.properties)
	 */
	public MyParseError(Localization loc, String errorName) {
		super(loc, errorName);
	}

	/**
	 * @param app
	 *            application
	 * @param strs
	 *            lines of the error
	 */
	public MyParseError(Localization loc, String[] strs) {
		// set localized message
		super(loc, strs);
	}

	@Override
	public String getLocalizedMessage() {
		return loc.getError("InvalidInput") + " :\n"
				+ super.getLocalizedMessage();
	}
}
