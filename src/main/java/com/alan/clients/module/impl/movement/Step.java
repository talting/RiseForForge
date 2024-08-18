package com.alan.clients.module.impl.movement;

import com.alan.clients.module.Module;
import com.alan.clients.module.impl.movement.step.Hypixel2Step;
import com.alan.clients.module.impl.movement.step.HypixelStep;
import com.alan.clients.module.setting.impl.ModeValue;

public class Step extends Module {
    private final ModeValue mode;

    public Step() {
        super("Step", category.movement);
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new HypixelStep("Hypixel 1.5", this))
                .add(new Hypixel2Step("Hypixel", this))
        );
    }

    @Override
    public void onEnable() {
        mode.enable();
    }

    @Override
    public void onDisable() {
        mode.disable();
    }

    @Override
    public String getInfo() {
        return mode.getSubModeValues().get((int) mode.getInput()).getPrettyName();
    }
}
