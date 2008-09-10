/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.internal.win32;

public class TVSORTCB {
	/** @field cast=(HTREEITEM) */
	public int /*long*/ hParent;
	/** @field cast=(PFNTVCOMPARE) */
	public int /*long*/ lpfnCompare;
	/** @field cast=(LPARAM) */
	public int /*long*/ lParam;
	public static final int sizeof = OS.TVSORTCB_sizeof ();
}
