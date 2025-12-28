package com.example.client;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientMod implements ModInitializer {
    public static final String MOD_ID = "client_mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Item CUSTOM_FEATHER = new Item(new Item.Settings());

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Client Mod for 1.21.1");

        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "custom_feather"), CUSTOM_FEATHER);
        
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            content.add(CUSTOM_FEATHER);
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("clientmod")
                .executes(context -> {
                    context.getSource().sendFeedback(() -> Text.literal("Client Mod Command Executed!"), false);
                    return 1;
                }));
        });
    }
}