/* Copyright (c) 2019 Galaxtone
 * MIT License.
*/

package com.galaxtone.upc;

import com.galaxtone.upc.item.ReinforcedIgniterItem;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class ModItems {

	public static final Item REINFORCED_IGNITER = new ReinforcedIgniterItem();

	private ModItems() {}

	private static void registerItem(String name, Item item) {
		Registry.register(Registry.ITEM,
				new Identifier(ModMain.MOD_ID, name), item);
	}

	static void registerItems() {
		registerItem("reinforced_igniter", REINFORCED_IGNITER);
	}
}
