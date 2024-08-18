package com.alan.clients.module.impl.movement.speed;

import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.movement.speed.hypixel.GroundStrafeSpeed;
import com.alan.clients.module.impl.movement.speed.hypixel.HypixelGroundSpeed;
import com.alan.clients.module.impl.movement.speed.hypixel.HypixelLowHopSpeed;
import com.alan.clients.module.impl.movement.speed.hypixel.RiseWatchdogSpeed;
import com.alan.clients.module.setting.impl.ModeValue;
import com.alan.clients.module.setting.impl.SubMode;
import org.jetbrains.annotations.NotNull;

public class HypixelSpeed extends SubMode<Speed> {
    private final ModeValue mode;

    public HypixelSpeed(String name, @NotNull Speed parent) {
        super(name, parent);
        this.registerSetting(mode = new ModeValue("Hypixel mode", this)
                .add(new GroundStrafeSpeed("GroundStrafe", this))
                .add(new RiseWatchdogSpeed("Rise", this))
                .add(new HypixelGroundSpeed("Ground", this))
                .add(new HypixelLowHopSpeed("Rise", this))
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
}
