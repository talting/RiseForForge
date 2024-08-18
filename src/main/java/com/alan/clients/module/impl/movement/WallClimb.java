package com.alan.clients.module.impl.movement;

import com.alan.clients.module.Module;
import com.alan.clients.module.impl.movement.wallclimb.TestWallClimb;
import com.alan.clients.module.impl.movement.wallclimb.IntaveWallClimb;
import com.alan.clients.module.impl.movement.wallclimb.VulcanWallClimb;
import com.alan.clients.module.setting.impl.ModeValue;

public class WallClimb extends Module {
    private final ModeValue mode;

    public WallClimb() {
        super("WallClimb", category.movement);
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new IntaveWallClimb("Intave", this))
                .add(new VulcanWallClimb("Vulcan", this))
                .add(new TestWallClimb("Test", this))
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
