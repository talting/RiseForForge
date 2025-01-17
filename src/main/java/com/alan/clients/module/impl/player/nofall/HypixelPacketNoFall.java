package com.alan.clients.module.impl.player.nofall;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.impl.combat.KillAura;
import com.alan.clients.module.impl.player.NoFall;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.ModeSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.MoveUtil;
import com.alan.clients.utility.Utils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class HypixelPacketNoFall extends SubMode<NoFall> {
    private final ModeSetting calcateMode;
    private final ButtonSetting prediction;
    private final SliderSetting minFallDistance;
    private final ButtonSetting notWhileKillAura;

    private double fallDistance = 0;
    private boolean timed = false;

    public HypixelPacketNoFall(String name, @NotNull NoFall parent) {
        super(name, parent);
        this.registerSetting(calcateMode = new ModeSetting("Calcate mode", new String[]{"Position", "Motion"}, 0));
        this.registerSetting(prediction = new ButtonSetting("Prediction", false));
        this.registerSetting(minFallDistance = new SliderSetting("Minimum fall distance", 3.0, 0.0, 8.0, 0.1));
        this.registerSetting(notWhileKillAura = new ButtonSetting("Not while killAura", true));
    }

    @Override
    public void onDisable() {
        fallDistance = 0;
        if (timed)
            Utils.resetTimer();
        timed = false;
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (mc.thePlayer.onGround)
            fallDistance = 0;
        else {
            switch ((int) calcateMode.getInput()) {
                case 0:
                    fallDistance += (float) Math.max(mc.thePlayer.lastTickPosY - event.getPosY(), 0);
                    break;
                case 1:
                    fallDistance += (float) Math.max(-mc.thePlayer.motionY, 0);
                    break;
            }

            if (prediction.isToggled()) {
                fallDistance -= MoveUtil.predictedMotion(mc.thePlayer.motionY, 1);  // motion should be nev on falling
            }
        }

        if (fallDistance >= minFallDistance.getInput() && !parent.noAction() && !(notWhileKillAura.isToggled() && KillAura.target != null)) {
            Utils.getTimer().timerSpeed = (float) 0.5;
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
            fallDistance = 0;
            timed = true;
        } else if (timed) {
            Utils.resetTimer();
            timed = false;
        }
    }
}
