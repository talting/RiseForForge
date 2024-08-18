package com.alan.clients.module.impl.other;

import com.alan.clients.Rise;
import com.alan.clients.event.ReceivePacketEvent;
import java.util.concurrent.TimeUnit;
import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.DescriptionSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.utility.PacketUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C16PacketClientStatus;
import org.jetbrains.annotations.NotNull;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoRespawn extends Module{
    private final SliderSetting delay;
    public AutoRespawn() {
        super("AutoRespawn", category.other);
        this.registerSetting(new DescriptionSetting("Automatically respawns you after you die."));
        this.registerSetting(delay = new SliderSetting("Delay (ms)", 0, 0, 100000, 1000));
    }
   @SubscribeEvent
    public void onReceive(@NotNull ReceivePacketEvent event) {
        if(Minecraft.getMinecraft().thePlayer.isDead){
            Rise.getExecutor().schedule(() ->
                PacketUtils.sendPacketNoEvent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN)),
                (long) delay.getInput(),
                TimeUnit.MILLISECONDS);
        }
    };
}
