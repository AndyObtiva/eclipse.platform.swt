/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.tests.junit;


import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import junit.framework.*;
import junit.textui.*;

/**
 * Automated Test Suite for class org.eclipse.swt.graphics.Cursor
 *
 * @see org.eclipse.swt.graphics.Cursor
 */
public class Test_org_eclipse_swt_graphics_Cursor extends SwtTestCase {

Display display;

public Test_org_eclipse_swt_graphics_Cursor(String name) {
	super(name);
}

public static void main(String[] args) {
	TestRunner.run(suite());
}

protected void setUp() {
	display = new Display();
}

protected void tearDown() {
	display.dispose();
}

public void test_ConstructorLorg_eclipse_swt_graphics_DeviceI() {
	// Test new Cursor(Device device, int style)
	// IllegalArgumentException when an unknown style is specified

	Cursor cursor = new Cursor(display, SWT.CURSOR_ARROW);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_WAIT);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_CROSS);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_APPSTARTING);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_HELP);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZEALL);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZENESW);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZENS);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZENWSE);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZEWE);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZEN);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZES);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZEE);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZEW);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZENE);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZESE);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZESW);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_SIZENW);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_UPARROW);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_IBEAM);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_NO);
	cursor.dispose();

	cursor = new Cursor(display, SWT.CURSOR_HAND);
	cursor.dispose();

	// device == null (valid)
	cursor = new Cursor(null, SWT.CURSOR_ARROW);
	cursor.dispose();

	// illegal argument, style > SWT.CURSOR_HAND (21)
	try {
		cursor = new Cursor(display, 100);
		cursor.dispose();
		fail("No exception thrown for style > SWT.CURSOR_HAND (21)");
	} catch (IllegalArgumentException e) {
	}

	// illegal argument, style < 0
	try {
		cursor = new Cursor(display, -100);
		cursor.dispose();
		fail("No exception thrown for style < 0");
	} catch (IllegalArgumentException e) {
	}
}

public void test_ConstructorLorg_eclipse_swt_graphics_DeviceLorg_eclipse_swt_graphics_ImageDataLorg_eclipse_swt_graphics_ImageDataII() {
	// Test new Cursor(Device device, ImageData source, ImageData mask, int hotspotX, int hotspotY)
	ImageLoader loader = new ImageLoader();
	ImageData source = loader.load(SwtTestCase.class.getResourceAsStream("dot.gif"))[0];
	ImageData mask = source.getTransparencyMask();
	Cursor cursor = new Cursor(display, source, mask, 0, 0);
	cursor.dispose();
}
public void test_dispose() {
	// tested in test_isDisposed
}

public void test_equalsLjava_lang_Object() {
	/* Note: Two cursors are only considered equal if their handles are equal.
	 * So since Windows reuses cursor handles, and other platforms do not,
	 * it does not make sense to test whether cursor.equals(sameCursor).
	 */
	Cursor cursor = new Cursor(display, SWT.CURSOR_WAIT);
	Cursor otherCursor = new Cursor(display, SWT.CURSOR_CROSS);
	try {
		// Test Cursor.equals(Object)
		assertTrue("!cursor.equals((Object)null)", !cursor.equals((Object)null));

		// Test Cursor.equals(Cursor)
		assertTrue("!cursor.equals((Cursor)null)", !cursor.equals((Cursor)null));
		assertTrue("cursor.equals(cursor)", cursor.equals(cursor));
		assertTrue("!cursor.equals(otherCursor)", !cursor.equals(otherCursor));
	} finally {
		cursor.dispose();
		otherCursor.dispose();
	}
}

public void test_hashCode() {
	warnUnimpl("Test test_hashCode not written");
}

public void test_isDisposed() {
	// Test Cursor.isDisposed() false
	Cursor cursor = new Cursor(display, SWT.CURSOR_WAIT);
	try {
		assertTrue("Cursor should not be disposed", !cursor.isDisposed());
	} finally {
		// Test Cursor.isDisposed() true
		cursor.dispose();
		assertTrue("Cursor should be disposed", cursor.isDisposed());
	}
}

public void test_toString() {
	Cursor cursor = new Cursor(display, SWT.CURSOR_WAIT);
	assertNotNull(cursor.toString());
	assertTrue(cursor.toString().length() > 0);
}

public void test_win32_newLorg_eclipse_swt_graphics_DeviceI() {
	// do not test - Windows only
}

public static Test suite() {
	TestSuite suite = new TestSuite();
	java.util.Vector methodNames = methodNames();
	java.util.Enumeration e = methodNames.elements();
	while (e.hasMoreElements()) {
		suite.addTest(new Test_org_eclipse_swt_graphics_Cursor((String)e.nextElement()));
	}
	return suite;
}
public static java.util.Vector methodNames() {
	java.util.Vector methodNames = new java.util.Vector();
	methodNames.addElement("test_ConstructorLorg_eclipse_swt_graphics_DeviceI");
	methodNames.addElement("test_ConstructorLorg_eclipse_swt_graphics_DeviceLorg_eclipse_swt_graphics_ImageDataLorg_eclipse_swt_graphics_ImageDataII");
	methodNames.addElement("test_dispose");
	methodNames.addElement("test_equalsLjava_lang_Object");
	methodNames.addElement("test_hashCode");
	methodNames.addElement("test_isDisposed");
	methodNames.addElement("test_toString");
	methodNames.addElement("test_win32_newLorg_eclipse_swt_graphics_DeviceI");
	return methodNames;
}
protected void runTest() throws Throwable {
	if (getName().equals("test_ConstructorLorg_eclipse_swt_graphics_DeviceI")) test_ConstructorLorg_eclipse_swt_graphics_DeviceI();
	else if (getName().equals("test_ConstructorLorg_eclipse_swt_graphics_DeviceLorg_eclipse_swt_graphics_ImageDataLorg_eclipse_swt_graphics_ImageDataII")) test_ConstructorLorg_eclipse_swt_graphics_DeviceLorg_eclipse_swt_graphics_ImageDataLorg_eclipse_swt_graphics_ImageDataII();
	else if (getName().equals("test_dispose")) test_dispose();
	else if (getName().equals("test_equalsLjava_lang_Object")) test_equalsLjava_lang_Object();
	else if (getName().equals("test_hashCode")) test_hashCode();
	else if (getName().equals("test_isDisposed")) test_isDisposed();
	else if (getName().equals("test_toString")) test_toString();
	else if (getName().equals("test_win32_newLorg_eclipse_swt_graphics_DeviceI")) test_win32_newLorg_eclipse_swt_graphics_DeviceI();
}
}
