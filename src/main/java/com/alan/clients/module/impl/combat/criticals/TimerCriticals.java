package com.alan.clients.module.impl.combat.criticals;

import com.alan.clients.module.ModuleManager;
import com.alan.clients.module.impl.combat.Criticals;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;

public class TimerCriticals extends SubMode<Criticals> {
    private final SliderSetting timer;
    private final SliderSetting maxTimerTime;
    private final SliderSetting chance;
    private final ButtonSetting stopOnHurt;

    private long startTimer = -1;
    private boolean delayed = false;

    public TimerCriticals(String name, @NotNull Criticals parent) {
        super(name, parent);
        this.registerSetting(timer = new SliderSetting("Timer", 0.5, 0, 1, 0.1));
        this.registerSetting(maxTimerTime = new SliderSetting("Max timer time", 2000, 100, 3000, 100, "ms"));
        this.registerSetting(chance = new SliderSetting("Chance", 90, 0, 100, 1, "%"));
        this.registerSetting(stopOnHurt = new ButtonSetting("Stop on hurt", true));
    }

    @Override
    public void onDisable() {
        if (startTimer != -1)
            Utils.resetTimer();
        startTimer = -1;
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        if (mc.thePlayer == null) return;

        if (startTimer != -1) {
            if (mc.thePlayer.onGround || delayed
                    || (mc.thePlayer.hurtTime > 0 && stopOnHurt.isToggled())
                    || System.currentTimeMillis() - startTimer > maxTimerTime.getInput()) {
                Utils.resetTimer();
                startTimer = -1;
            }
        } else if (mc.thePlayer.motionY < 0 && !mc.thePlayer.onGround && !delayed) {
            if (Utils.getTimer().timerSpeed != (float) timer.getInput() && chance.getInput() != 100 && Math.random() * 100 > chance.getInput()) {
                delayed = true;
                return;
            }

            if (Utils.isTargetNearby(ModuleManager.killAura.isEnabled() ? ModuleManager.killAura.attackRange.getInput() : 3)) {
                startTimer = System.currentTimeMillis();
                Utils.getTimer().timerSpeed = (float) timer.getInput();
            }
        } else if (mc.thePlayer.onGround) {
            delayed = false;
        }
    }
}
