package com.alan.clients.module.impl.combat.autoclicker;

import com.alan.clients.module.impl.combat.HitSelect;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.CoolDown;
import com.alan.clients.utility.Utils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

public class LowCPSAutoClicker extends SubMode<IAutoClicker> {
    private final SliderSetting minDelay = new SliderSetting("Min Delay", 500, 100, 3000, 100, "ms");
    private final SliderSetting maxDelay = new SliderSetting("Max Delay", 1000, 100, 3000, 100, "ms");
    private final boolean leftClick;
    private final boolean rightClick;
    private final boolean always;

    private final CoolDown clickStopWatch = new CoolDown(0);
    private int ticksDown;
    private long nextSwing;

    public LowCPSAutoClicker(String name, @NotNull IAutoClicker parent, boolean left, boolean always) {
        super(name, parent);
        this.leftClick = left;
        this.rightClick = !left;
        this.always = always;

        this.registerSetting(minDelay, maxDelay);
    }

    @Override
    public void guiUpdate() {
        Utils.correctValue(minDelay, maxDelay);
    }

    @Override
    public void onUpdate() {
        clickStopWatch.setCooldown(nextSwing);
        if (clickStopWatch.hasFinished()) {
            final long delay = (long) (Utils.randomizeDouble(minDelay.getInput(), maxDelay.getInput()));

            if (Mouse.isButtonDown(0) || always) {
                ticksDown++;
            } else {
                ticksDown = 0;
            }

            this.nextSwing = delay;

            if (rightClick && ((Mouse.isButtonDown(1) && !Mouse.isButtonDown(0)) || always)) {
                parent.click();
            }

            if (leftClick && ticksDown > 1 && (!Mouse.isButtonDown(1) || always)) {
                parent.click();
            }

            this.clickStopWatch.start();
        }
    }
}
