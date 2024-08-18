package com.alan.clients.module.impl.movement;

import com.alan.clients.event.MoveEvent;
import com.alan.clients.event.MoveInputEvent;
import com.alan.clients.event.RotationEvent;
import com.alan.clients.event.SendPacketEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.impl.other.RotationHandler;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.ModeSetting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class AirStuck extends Module {
    private final ModeSetting mode;
    private final ButtonSetting clearMotion;

    private float yaw, pitch;
    private double motionX, motionY, motionZ;

    public AirStuck() {
        super("AirStuck", category.movement);
        this.registerSetting(mode = new ModeSetting("Mode", new String[]{"CanRotate", "NoRotate", "NoPacket", "FakeMove"}, 1));
        this.registerSetting(clearMotion = new ButtonSetting("Clear motion", true));
    }

    @SubscribeEvent
    public void onMove(@NotNull MoveEvent event) {
        event.setCanceled(true);
        mc.thePlayer.motionX = motionX;
        mc.thePlayer.motionY = motionY;
        mc.thePlayer.motionZ = motionZ;
    }

    @SubscribeEvent
    public void onSendPacket(@NotNull SendPacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            switch ((int) mode.getInput()) {
                case 0:
                    break;
                case 2:
                    event.setCanceled(true);
                    break;
                case 1:
                    event.setPacket(new C03PacketPlayer(((C03PacketPlayer) event.getPacket()).isOnGround()));
                    break;
                case 3:
                    event.setPacket(new C03PacketPlayer.C06PacketPlayerPosLook(
                            mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ,
                            RotationHandler.getRotationYaw(), RotationHandler.getRotationPitch(),
                            mc.thePlayer.onGround
                    ));
                    break;
            }
        }
    }

    @Override
    public void onEnable() {
        yaw = RotationHandler.getRotationYaw();
        pitch = RotationHandler.getRotationPitch();
        motionX = mc.thePlayer.motionX;
        motionY = mc.thePlayer.motionY;
        motionZ = mc.thePlayer.motionZ;
    }

    @Override
    public void onDisable() {
        if (clearMotion.isToggled()) {
            mc.thePlayer.motionX = mc.thePlayer.motionY = mc.thePlayer.motionZ = 0;
        }
    }

    @SubscribeEvent
    public void onRotation(RotationEvent event) {
        // visual only
        switch ((int) mode.getInput()) {
            case 1:
            case 2:
                event.setYaw(yaw);
                event.setPitch(pitch);
                break;
        }
    }
}
