/* Copyright (c) 2019 Galaxtone
 * MIT License.
*/

package com.galaxtone.upc.util;

public interface IAreaHelper {

	public boolean isValid();

	public boolean hasPortalBlocks();
	public boolean hasNonObsidianBlocks();

	public void createPortal();
}
