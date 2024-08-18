package com.alan.clients.module.impl.movement;

import com.alan.clients.Rise;
import com.alan.clients.event.ReceivePacketEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.utility.Utils;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;

public class VClip extends Module {
    private final SliderSetting distance;
    private final SliderSetting cancelS08;
    private final ButtonSetting sendMessage;

    public VClip() {
        super("VClip", Module.category.movement, 0);
        this.registerSetting(distance = new SliderSetting("Distance", 3.0, -20.0, 20.0, 0.5));
        this.registerSetting(cancelS08 = new SliderSetting("Cancel S08", 0, 0, 20, 1, "ticks"));
        this.registerSetting(sendMessage = new ButtonSetting("Send message", true));
    }

    public void onEnable() {
        final double distance = this.distance.getInput();
        if (this.distance.getInput() != 0.0D) {
            mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + distance, mc.thePlayer.posZ);
            if (sendMessage.isToggled()) {
                Utils.sendMessage("&7Teleported you " + ((distance > 0.0) ? "upwards" : "downwards") + " by &b" + distance + " &7blocks.");
            }
        }

        if (cancelS08.getInput() > 0) {
            Rise.getExecutor().schedule(this::disable, (long) cancelS08.getInput() * 50, TimeUnit.MILLISECONDS);
        } else {
            disable();
        }
    }

    @SubscribeEvent
    public void onReceivePacket(ReceivePacketEvent event) {
        if (cancelS08.getInput() > 0 && event.getPacket() instanceof S08PacketPlayerPosLook) {
            event.setCanceled(true);
        }
    }
}