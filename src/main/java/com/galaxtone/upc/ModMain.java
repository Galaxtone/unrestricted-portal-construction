/* Copyright (c) 2019 Galaxtone
 * MIT License.
*/

package com.galaxtone.upc;

import net.fabricmc.api.ModInitializer;

public final class ModMain implements ModInitializer {

	public static final String MOD_ID = "upc";

	private static boolean isImmersivePresent = false;

	private ModMain() {
	}

	@Override
	public void onInitialize() {
		ModItems.registerItems();
		ModCompatibility.initialize();
	}
}
