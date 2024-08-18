package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.Rise;
import com.alan.clients.event.PostVelocityEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.ModeSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.module.setting.utils.ModeOnly;
import com.alan.clients.utility.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;

public class LegitVelocity extends SubMode<Velocity> {
    private final ButtonSetting jumpInInv;
    private final ModeSetting jumpDelayMode;
    private final SliderSetting minDelay;
    private final SliderSetting maxDelay;
    private final SliderSetting chance;
    private final ButtonSetting targetNearbyCheck;
    private final ButtonSetting ignoreLiquid;

    public LegitVelocity(String name, Velocity parent) {
        super(name, parent);
        this.registerSetting(jumpInInv = new ButtonSetting("Jump in inv", false));
        this.registerSetting(jumpDelayMode = new ModeSetting("Jump delay mode", new String[]{"Delay", "Chance"}, 1));
        this.registerSetting(minDelay = new SliderSetting("Min delay", 0, 0, 150, 1, "ms", new ModeOnly(jumpDelayMode, 0)));
        this.registerSetting(maxDelay = new SliderSetting("Max delay", 0, 0, 150, 1, "ms", new ModeOnly(jumpDelayMode, 0)));
        this.registerSetting(chance = new SliderSetting("Chance", 80, 0, 100, 1, "%", new ModeOnly(jumpDelayMode, 1)));
        this.registerSetting(targetNearbyCheck = new ButtonSetting("Target nearby check", false));
        this.registerSetting(ignoreLiquid = new ButtonSetting("Ignore liquid", true));
    }

    @Override
    public void guiUpdate() {
        Utils.correctValue(minDelay, maxDelay);
    }

    @SubscribeEvent
    public void onPostVelocity(PostVelocityEvent event) {
        if (Utils.nullCheck()) {
            if (mc.thePlayer.maxHurtTime <= 0)
                return;
            if (ignoreLiquid.isToggled() && Utils.inLiquid())
                return;
            if (targetNearbyCheck.isToggled() && !Utils.isTargetNearby())
                return;

            switch ((int) jumpDelayMode.getInput()) {
                case 0:
                    long delay = (long) (Math.random() * (maxDelay.getInput() - minDelay.getInput()) + minDelay.getInput());
                    if (maxDelay.getInput() == 0 || delay == 0) {
                        if (canJump())
                            mc.thePlayer.jump();
                    } else {
                        Rise.getExecutor().schedule(() -> {
                            if (canJump())
                                mc.thePlayer.jump();
                        }, delay, TimeUnit.MILLISECONDS);
                    }
                    break;
                case 1:
                    if (chance.getInput() == 100 || Math.random() * 100 >= chance.getInput()) {
                        if (canJump())
                            mc.thePlayer.jump();
                    }
                    break;
            }
        }
    }

    private boolean canJump() {
        return mc.thePlayer.onGround && (jumpInInv.isToggled() || mc.currentScreen == null);
    }
}
