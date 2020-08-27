package com.ThishExercise.InvisibleMobs;

import net.minecraft.block.Blocks;
import net.minecraftforge.client.settings.KeyBindingMap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("invisiblemobs")
public class InvisibleMobs
{

    public static KeyBindingMap allBinds;
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();



    public InvisibleMobs() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("Loading Invisible Mobs");
        MinecraftForge.EVENT_BUS.register(new EventManager());
    }

}
