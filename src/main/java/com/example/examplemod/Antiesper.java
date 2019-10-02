package com.example.examplemod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod(modid = Antiesper.MODID, version = Antiesper.VERSION, clientSideOnly = true)
@Mod.EventBusSubscriber(Side.CLIENT) public class Antiesper {
    public static final String MODID = "@MODID@";
    public static final String VERSION = "@VERSION@";

    public static Map<EntityPlayer, Boolean> visibilityMap = new ConcurrentHashMap<>();

    @SubscribeEvent public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START)
            return;

        Minecraft minecraft = Minecraft.getMinecraft();

        if (minecraft.world == null)
            return;

        EntityPlayer player = minecraft.player;

        List<EntityPlayer> players =
            minecraft.world.getPlayers(EntityPlayer.class, otherPlayer -> true);

        for (EntityPlayer otherPlayer : players) {
            visibilityMap.put(otherPlayer, isVisible(player, otherPlayer));
        }
    }

    private static boolean isVisible(EntityPlayer player, EntityPlayer otherPlayer) {
        Vec3d pos = player.getPositionEyes(0);
        Vec3d otherPos = otherPlayer.getPositionEyes(0);

        if (pos.distanceTo(otherPos) > 50)
            return false;

        return otherPlayer.canEntityBeSeen(player);
    }

    @SubscribeEvent public static void hideNickname(RenderLivingEvent.Specials.Pre event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) entity;

        if (!visibilityMap.getOrDefault(player, false))
            event.setCanceled(true);
    }
}
