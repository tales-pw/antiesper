package com.example.examplemod;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Antiesper.MODID, version = Antiesper.VERSION) @Mod.EventBusSubscriber(Side.CLIENT)
public class Antiesper {
    public static final String MODID = "@MODID@";
    public static final String VERSION = "@VERSION@";

    @SubscribeEvent public static void hideNickname(RenderLivingEvent.Specials.Pre event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof EntityPlayer))
            return;

        EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();

        if (!player.canEntityBeSeen(entity))
            event.setCanceled(true);
    }
}
