package com.alan.clients.module.impl.render;

import com.alan.clients.event.ReceivePacketEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.DescriptionSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoHurtCam extends Module {
    public SliderSetting multiplier;
    private final ButtonSetting noHurtAnime;

    public NoHurtCam() {
        super("NoHurtCam", category.render);
        this.registerSetting(new DescriptionSetting("Default is 14x multiplier."));
        this.registerSetting(multiplier = new SliderSetting("Multiplier", 14, -40, 40, 1));
        this.registerSetting(noHurtAnime = new ButtonSetting("No hurt anime", false));
    }

    @SubscribeEvent
    public void onReceivePacket(ReceivePacketEvent event) {
        if (noHurtAnime.isToggled() && event.getPacket() instanceof S0BPacketAnimation) {
            S0BPacketAnimation packet = (S0BPacketAnimation) event.getPacket();

            if (packet.getEntityID() == mc.thePlayer.getEntityId() && packet.getAnimationType() == 1)
                event.setCanceled(true);
        }
    }
}
