package com.alan.clients.module.impl.player.nofall;

import com.alan.clients.event.SendPacketEvent;
import com.alan.clients.module.impl.player.NoFall;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.Reflection;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VulcanNoFall extends SubMode<NoFall> {
    private final SliderSetting minFallDistance;

    public static final List<Integer> maxModCount = new ArrayList<>(Arrays.asList(3, 2, 2));
    private int currentModCount = 0;
    private int hasModCount = 0;

    public VulcanNoFall(String name, @NotNull NoFall parent) {
        super(name, parent);
        this.registerSetting(minFallDistance = new SliderSetting("Minimum fall distance", 3.0, 0.0, 8.0, 0.1));
    }

    @SubscribeEvent
    public void onPacketSend(@NotNull SendPacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer && mc.thePlayer.fallDistance > minFallDistance.getInput()) {
            if (currentModCount > maxModCount.get(hasModCount % maxModCount.size())) {
                return;
            }

            try {
                Reflection.C03PacketPlayerOnGround.set(event.getPacket(), true);
                mc.thePlayer.fallDistance = 0;
                mc.thePlayer.setVelocity(0, 0, 0);
                currentModCount++;
            } catch (IllegalAccessException ignored) {
            }
        }
    }

    public void onUpdate() {
        if (mc.thePlayer.onGround && currentModCount > 0) {
            hasModCount++;
            currentModCount = 0;
        }
    }
}
