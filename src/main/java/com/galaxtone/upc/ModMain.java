/* Copyright (c) 2019 Galaxtone
 * MIT License.
*/

package com.galaxtone.upc;

import net.fabricmc.api.ModInitializer;

public final class ModMain implements ModInitializer {

	public static final String MOD_ID = "upc";

	@Override
	public void onInitialize() {
		ModItems.registerItems();
	}
}
