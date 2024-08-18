package com.alan.clients.module.impl.player;

import com.alan.clients.event.ReceivePacketEvent;
import com.alan.clients.mixins.impl.network.S08PacketPlayerPosLookAccessor;
import com.alan.clients.module.Module;
import com.alan.clients.module.impl.other.RotationHandler;
import com.alan.clients.module.setting.impl.ModeSetting;
import com.alan.clients.utility.Reflection;
import com.alan.clients.utility.Utils;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRotate extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", new String[]{"Cancel", "Silent"}, 0);

    public NoRotate() {
        super("NoRotate", category.player);
        this.registerSetting(mode);
    }

    @SubscribeEvent
    public void onReceivePacket(ReceivePacketEvent event) {
        if (Utils.nullCheck() && event.getPacket() instanceof S08PacketPlayerPosLook) {
            final S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
            switch ((int) mode.getInput()) {
                case 1:
                    RotationHandler.setRotationYaw(packet.getYaw());
                    RotationHandler.setRotationPitch(packet.getPitch());
                case 0:
                    // Reflection is TOO SLOW
                    final S08PacketPlayerPosLookAccessor p = (S08PacketPlayerPosLookAccessor) packet;
                    p.setYaw(mc.thePlayer.rotationYaw);
                    p.setPitch(mc.thePlayer.rotationPitch);
                    break;
            }
        }
    }
}
