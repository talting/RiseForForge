package com.alan.clients.module.impl.combat.autoclicker;

import com.alan.clients.module.impl.other.RecordClick;
import com.alan.clients.module.setting.impl.SubMode;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

public class RecordAutoClicker extends SubMode<IAutoClicker> {
    private final boolean left;
    private final boolean always;

    public RecordAutoClicker(String name, @NotNull IAutoClicker parent, boolean left, boolean always) {
        super(name, parent);
        this.left = left;
        this.always = always;
    }

    @Override
    public void onUpdate() {
        if (!always && left ? !Mouse.isButtonDown(0) : !Mouse.isButtonDown(1))
            return;
        if (System.currentTimeMillis() < RecordClick.getNextClickTime())
            return;

        if (parent.click())
            RecordClick.click();
    }
}
