package com.alan.clients.module.impl.movement.fly;

import com.alan.clients.event.PrePlayerInputEvent;
import com.alan.clients.event.PreUpdateEvent;
import com.alan.clients.event.ReceivePacketEvent;
import com.alan.clients.module.impl.movement.Fly;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.MoveUtil;
import com.alan.clients.utility.PacketUtils;
import com.alan.clients.utility.Utils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class NCPClipFly extends SubMode<Fly> {
    private int offGroundTicks = 0;
    private boolean started, notUnder, clipped, teleport;

    public NCPClipFly(String name, @NotNull Fly parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void onPreUpdate(PreUpdateEvent event) {
        if (mc.thePlayer.onGround) {
            offGroundTicks = 0;
        } else {
            offGroundTicks++;
        }
    }

    @SubscribeEvent
    public void onPrePlayerInput(PrePlayerInputEvent event) {
        final AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, 1, 0);

        if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty() || started) {
            switch (offGroundTicks) {
                case 0:
                    if (notUnder) {
                        if (clipped) {
                            started = true;
                            event.setSpeed(10);
                            mc.thePlayer.motionY = 0.42f;
                            notUnder = false;
                        }
                    }
                    break;
                case 1:
                    if (started)
                        event.setSpeed(9.6);
                    break;
            }
        } else {
            notUnder = true;

            if (clipped) return;

            clipped = true;

            PacketUtils.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
            PacketUtils.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 0.1, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
            PacketUtils.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));

            teleport = true;
        }

        MoveUtil.strafe();

        Utils.getTimer().timerSpeed = 0.4f;
    }

    @SubscribeEvent
    public void onReceivePacket(ReceivePacketEvent event) {
        if (teleport) {
            event.setCanceled(true);
            teleport = false;
        }
    }

    @Override
    public void onEnable() {
        notUnder = false;
        started = false;
        clipped = false;
        teleport = false;
    }

    @Override
    public void onDisable() {
        MoveUtil.stop();
    }
}
