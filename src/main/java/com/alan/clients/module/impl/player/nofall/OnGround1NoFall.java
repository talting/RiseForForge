package com.alan.clients.module.impl.player.nofall;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.impl.player.NoFall;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.PacketUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class OnGround1NoFall extends SubMode<NoFall> {
    private final SliderSetting minFallDist;

    public OnGround1NoFall(String name, @NotNull NoFall parent) {
        super(name, parent);
        this.registerSetting(minFallDist = new SliderSetting("Min fall distance", 4, 1, 24, 1));
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (mc.thePlayer.fallDistance > minFallDist.getInput() && !parent.noAction()) {
            PacketUtils.sendPacket(new C03PacketPlayer(true));
        }
    }
}
