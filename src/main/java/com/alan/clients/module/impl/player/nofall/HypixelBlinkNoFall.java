package com.alan.clients.module.impl.player.nofall;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.impl.player.NoFall;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.BlockUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import static com.alan.clients.module.ModuleManager.blink;
import static com.alan.clients.module.ModuleManager.scaffold;

public class HypixelBlinkNoFall extends SubMode<NoFall> {
    private final SliderSetting minFallDistance;

    private boolean blinked = false;
    private boolean prevOnGround = false;
    private double fallDistance = 0;

    public HypixelBlinkNoFall(String name, @NotNull NoFall parent) {
        super(name, parent);
        this.registerSetting(minFallDistance = new SliderSetting("Minimum fall distance", 3.0, 0.0, 8.0, 0.1));
    }

    @Override
    public void onDisable() {
        if (blinked) {
            blink.disable();
            blinked = false;
        }
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (mc.thePlayer.onGround || parent.noAction()) {
            if (blinked) {
                blink.disable();
                blinked = false;
            }

            this.prevOnGround = mc.thePlayer.onGround;
        } else if (this.prevOnGround) {
            if (shouldBlink()) {
                blink.enable();
                blinked = true;
            }

            prevOnGround = false;
        } else if (BlockUtils.isBlockUnder() && blink.isEnabled() && (this.fallDistance - mc.thePlayer.motionY) >= minFallDistance.getInput()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            this.fallDistance = 0.0F;
        }
    }

    private boolean shouldBlink() {
        return !mc.thePlayer.onGround && !BlockUtils.isBlockUnder((int) Math.floor(minFallDistance.getInput())) && BlockUtils.isBlockUnder() && !scaffold.isEnabled();
    }
}
