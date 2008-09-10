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

public class PROCESS_INFORMATION {
	/** @field cast=(HANDLE) */
	public int /*long*/ hProcess;
	/** @field cast=(HANDLE) */
	public int /*long*/ hThread;
	public int dwProcessId;
	public int dwThreadId;
	public static int sizeof = Win32.PROCESS_INFORMATION_sizeof ();
}
