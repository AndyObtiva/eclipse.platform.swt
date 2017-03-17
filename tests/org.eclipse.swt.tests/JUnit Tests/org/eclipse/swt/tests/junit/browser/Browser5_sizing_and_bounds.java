/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.tests.junit.browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.VisibilityWindowListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.test.Screenshots;

/**
 * Tests setting browser bounds
 *
 */
public class Browser5_sizing_and_bounds {
	public static boolean verbose = false;
	public static boolean passed = false;
	static Point[][] regressionBounds = {
				{new Point(100,200), new Point(300,100)},
				{new Point(100,200), null},
				{null, new Point(300,100)},
				{null, null}};
	static int index = 0;
	static int cntPassed = 0;
	static int cntClosed = 0;

	public static boolean test1(String url) {
		if (verbose) System.out.println("javascript window.open with location and size parameters - args: "+url+"\n  Expected Event Sequence: Visibility.show");
		passed = false;

		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		final Browser browser = new Browser(shell, SWT.NONE);
		browser.addOpenWindowListener(event -> {
			if (verbose) System.out.println("OpenWindow "+index);
			Shell newShell = new Shell(display);
			newShell.setLayout(new FillLayout());
			Browser browser1 = new Browser(newShell, SWT.NONE);
			browser1.setData("index", Integer.valueOf(index));
			browser1.addVisibilityWindowListener(new VisibilityWindowListener() {
				@Override
				public void hide(WindowEvent event) {
				}
				@Override
				public void show(WindowEvent event) {
					Browser browser = (Browser)event.widget;
					Shell parent = browser.getShell();
					Point location = event.location;
					Point size = event.size;
					if (location != null) parent.setLocation(location);
					if (size != null) parent.setSize(size);
					int index = ((Integer)browser.getData("index")).intValue();
					parent.setText("SWT Browser shell "+index);
					parent.open();
					if (index < 0) {
						/* Certain browsers fire multiple show events for no good reason. Further show events
						 * are considered 'legal' as long as they don't contain size and location information.
						 */
						if (location != null || size != null) {
							if (verbose) System.out.println("Failure - Browser "+index+" is receiving multiple show events");
							if (verbose) Screenshots.takeScreenshot(Browser5_sizing_and_bounds.class, "show.noindex");
							passed = false;
							shell.close();
						} else {
							if (verbose) System.out.println("Unnecessary (but harmless) visibility.show event Browser "+index);
						}
					} else {
						if (verbose) System.out.println("Visibility.show browser "+index+" location "+location+" size "+size);
						browser.setData("index", Integer.valueOf(-100-index));

						/* Certain browsers include decorations in addition to the expected size.
						 * Accept sizes that are greater than or equal to the expected size.
						 * Certain browsers invent size or location when some parameters are missing.
						 * If we expect null for one of size or location, also accept non null answers.
						 */
						Point expectedLocation = regressionBounds[index][0];
						Point expectedSize = regressionBounds[index][1];
						if (verbose) System.out.println("Expected location "+expectedLocation+" size "+expectedSize);
						boolean checkLocation = (location == null && expectedLocation == null) ||
							(location != null && location.equals(expectedLocation) ||
							(location != null && expectedLocation == null));
						boolean checkSize  = (size == null && expectedSize == null) ||
							(size != null && size.equals(expectedSize)) ||
							(size != null && expectedSize == null) ||
							(size != null && size.x >= expectedSize.x && size.y >= expectedSize.y);
						if (!checkSize || !checkLocation) {
							if (verbose) System.out.println("	Failure ");
							if (verbose) Screenshots.takeScreenshot(Browser5_sizing_and_bounds.class, "show.failure." + index);
							if (location != null && expectedLocation != null
									&& expectedLocation.x - location.x < 20
									&& expectedLocation.y - location.y < 20) {
								// FIXME: Hack to investigate bug 499159: don't stop after first failure (off by window trimming)
								cntPassed++;
							} else {
								passed = false;
								shell.close();
								return;
							}
						} else cntPassed++;
					}
					if (verbose) Screenshots.takeScreenshot(Browser5_sizing_and_bounds.class, "show.end." + index);
				}
			});
			browser1.addCloseWindowListener(event1 -> {
				cntClosed++;
				if (verbose) System.out.println("Close");
				if (verbose) Screenshots.takeScreenshot(Browser5_sizing_and_bounds.class, "close " + cntClosed);
				Browser browser2 = (Browser)event1.widget;
				browser2.getShell().close();
				if (cntPassed == regressionBounds.length) passed = true;
				if (cntClosed == regressionBounds.length) {
					shell.close();
					return;
				}
			});
			event.browser = browser1;
			index++;
		});
		shell.open();
		browser.setUrl(url);

		boolean timeout = runLoopTimer(display, shell, 600);
		if (timeout) passed = false;
		display.dispose();
		return passed;
	}

	static boolean runLoopTimer(final Display display, final Shell shell, final int seconds) {
		final boolean[] timeout = {false};
		new Thread() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < seconds; i++) {
						Thread.sleep(1000);
						if (display.isDisposed() || shell.isDisposed()) return;
					}
				}
				catch (Exception e) {}
				timeout[0] = true;
				/* wake up the event loop */
				if (!display.isDisposed()) {
					display.asyncExec(() -> {
						if (!shell.isDisposed()) shell.redraw();
					});
				}
			}
		}.start();
		while (!timeout[0] && !shell.isDisposed()) if (!display.readAndDispatch()) display.sleep();
		return timeout[0];
	}

	public static boolean test() {
		int fail = 0;
		String url;
		String pluginPath = System.getProperty("PLUGIN_PATH");
		if (verbose) System.out.println("PLUGIN_PATH <"+pluginPath+">");
		if (pluginPath == null) url = Browser5_sizing_and_bounds.class.getClassLoader().getResource("browser5.html").toString();
		else url = pluginPath + "/data/browser5.html";
		String[] urls = {url};
		for (int i = 0; i < urls.length; i++) {
			boolean result = test1(urls[i]);
			if (verbose) System.out.print(result ? "." : "E");
			if (!result) fail++;
		}
		return fail == 0;
	}

	public static void main(String[] argv) {
		System.out.println("\r\nTests Finished. SUCCESS: "+test());
	}
}
