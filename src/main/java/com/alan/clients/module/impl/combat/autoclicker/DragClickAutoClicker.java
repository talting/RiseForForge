package com.alan.clients.module.impl.combat.autoclicker;

import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.Utils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

public class DragClickAutoClicker extends SubMode<IAutoClicker> {
    private final SliderSetting minLength = new SliderSetting("Min Length", 17, 1, 50, 1);
    private final SliderSetting maxLength = new SliderSetting("Max Length", 18, 1, 50, 1);
    private final SliderSetting minDelay = new SliderSetting("Min delay between", 6, 1, 20, 1);
    private final SliderSetting maxDelay = new SliderSetting("Max delay between", 6, 1, 20, 1);

    private int nextLength, nextDelay;

    private final boolean left;
    private final boolean always;

    public DragClickAutoClicker(String name, @NotNull IAutoClicker parent, boolean left, boolean always) {
        super(name, parent);
        this.left = left;
        this.always = always;

        this.registerSetting(minLength, maxLength, minDelay, maxDelay);
    }

    @Override
    public void guiUpdate() {
        Utils.correctValue(minLength, maxLength);
        Utils.correctValue(minDelay, maxDelay);
    }

    @Override
    public void onUpdate() {
        if (!always && left ? !Mouse.isButtonDown(0) : !Mouse.isButtonDown(1))
            return;

        if (nextLength < 0) {
            nextDelay--;

            if (nextDelay < 0) {
                nextDelay = Utils.randomizeInt(minDelay.getInput(), maxDelay.getInput());
                nextLength = Utils.randomizeInt(minLength.getInput(), maxLength.getInput());
            }
        } else if (Math.random() < 0.95) {
            nextLength--;
            parent.click();
        }
    }
}
