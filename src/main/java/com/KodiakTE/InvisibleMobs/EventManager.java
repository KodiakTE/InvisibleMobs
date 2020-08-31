package com.KodiakTE.InvisibleMobs;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;

import static com.ThishExercise.InvisibleMobs.InvisibleMobs.*;
import static net.minecraft.item.Items.GHAST_TEAR;

@Mod.EventBusSubscriber(modid = "invisiblemobs", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventManager {
    public static final KeyBinding runMod = new KeyBinding("Run Mod", GLFW_KEY_K, "Invisible Mobs");
    public static final KeyBinding stopMod = new KeyBinding("Stop Mod", GLFW_KEY_J, "Invisible Mobs");
    private static Minecraft mc = Minecraft.getInstance();
    private static boolean worldLoaded = false;
    public static boolean ModRunning = false;
    private static boolean InstructionsShown = false;
   // private static boolean lastGhastCheck = false;

    static {
        ClientRegistry.registerKeyBinding(runMod);
        ClientRegistry.registerKeyBinding(stopMod);
    }

    private static void modRunningNotification(){
        ITextComponent itextcomponent;
        if(ModRunning){
            itextcomponent = (new StringTextComponent("Invisible Mobs Is Now Running For ".concat(mc.player.getName().getString())).applyTextStyle(TextFormatting.ITALIC));
        }
        else if (!ModRunning){
            itextcomponent = (new StringTextComponent("Invisible Mobs Is Now Stopped For ".concat(mc.player.getName().getString())).applyTextStyle(TextFormatting.ITALIC));
        }
        else{
            itextcomponent = (new StringTextComponent("Problem Running Mod For ".concat(mc.player.getName().getString())).applyTextStyle(TextFormatting.ITALIC));
        }
        mc.player.sendMessage(itextcomponent);
    }


    @SubscribeEvent
    public static void waitForWorldLoad(EntityJoinWorldEvent event){
        //InvisibleMobs.LOGGER.info("World Loaded");
        if (event.getEntity().getType() == EntityType.ENDER_DRAGON){
            event.getEntity().setInvisible(true);
        }
        if(event.getEntity() == mc.player && !InstructionsShown) {
            //event.getEntity().getType() == EntityType.ENDER_DRAGON;
            ITextComponent  itextcomponent = (new StringTextComponent("Press K to run and J to stop Invisible Mobs ").applyTextStyle(TextFormatting.ITALIC));
            mc.player.sendMessage(itextcomponent);
            modRunningNotification();
            worldLoaded = true;
            InstructionsShown = true;
        }
    }

    @SubscribeEvent
    public static void runButtons(InputEvent.KeyInputEvent event){
        if(worldLoaded) {
            if (runMod.isPressed() && !ModRunning) {
                ModRunning = true;
                InvisibleMobs.LOGGER.info("Invisible Mobs Running");
                modRunningNotification();

            }
            if (stopMod.isPressed() && ModRunning) {
                ModRunning = false;
                InvisibleMobs.LOGGER.info("Invisible Mobs Stopped");
                modRunningNotification();
            }
        }
    }


    @SubscribeEvent
    public static void TurnMobsInvisible(LivingEvent livingEntity){

        //System.out.println("Ghast? " + checkGhastInHand());
        //if(lastGhastCheck != checkGhastInHand()) {
            //System.out.println("switch Detected");
        try {
            if(ModRunning) {
                if (!checkGhastInHand() && livingEntity.getEntity().getType() != EntityType.PLAYER && !livingEntity.getEntity().isInvisible()) {
                    livingEntity.getEntityLiving().setInvisible(true);
                } else if (checkGhastInHand() && livingEntity.getEntity().getType() != EntityType.PLAYER && livingEntity.getEntity().isInvisible()) {
                    livingEntity.getEntityLiving().setInvisible(false);
                }
            }
            else{
                livingEntity.getEntityLiving().setInvisible(false);
            }
        } catch(NullPointerException e){
            InvisibleMobs.LOGGER.info("exception raised since player hand not yet loaded");
        }
       // }
       // lastGhastCheck = checkGhastInHand();
    }


    public static boolean checkGhastInHand(){
       // try{
            if(worldLoaded) {
                //System.out.println(mc.player.getHeldItemMainhand().getItem());
                return mc.player.getHeldItemMainhand().getItem() == GHAST_TEAR;
            }
            else{
                return false;
            }
        //} catch (NullPointerException e){
        //    InvisibleMobs.LOGGER.info("exception raised since player hand not yet loaded");
        //    return false;
        //}
    }


}
