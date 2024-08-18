package com.alan.clients.module.impl.combat;

import com.alan.clients.module.Module;
import com.alan.clients.module.impl.combat.criticals.JumpCriticals;
import com.alan.clients.module.impl.combat.criticals.LagCriticals;
import com.alan.clients.module.impl.combat.criticals.NoGroundCriticals;
import com.alan.clients.module.impl.combat.criticals.TimerCriticals;
import com.alan.clients.module.setting.impl.ModeValue;

public class Criticals extends Module {
    private final ModeValue mode;

    public Criticals() {
        super("Criticals", category.combat, "Makes you get a critical hit every time you attack.");
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new NoGroundCriticals("NoGround", this))
                .add(new TimerCriticals("Timer", this))
                .add(new JumpCriticals("Jump", this))
                .add(new LagCriticals("Lag", this))
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
        return mode.getSelected().getPrettyInfo();
    }
}