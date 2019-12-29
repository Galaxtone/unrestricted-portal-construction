/* Copyright (c) 2019 Galaxtone
 * MIT License.
*/

package com.galaxtone.upc;

import net.fabricmc.loader.api.FabricLoader;

public final class ModCompatibility {

	private ModCompatibility() {
	}

	private static boolean isImmersivePresent;

	static void initialize() {
		FabricLoader loader = FabricLoader.getInstance();
		if (loader.isModLoaded("immersive_portals"))
			isImmersivePresent = true;
	}

	public static boolean isImmersivePresent() {
		return isImmersivePresent;
	}
}
