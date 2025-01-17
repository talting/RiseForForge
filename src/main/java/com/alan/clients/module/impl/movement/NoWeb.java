package com.alan.clients.module.impl.movement;

import com.alan.clients.module.Module;
import com.alan.clients.module.impl.movement.noweb.VulcanNoWeb;
import com.alan.clients.module.impl.movement.noweb.IgnoreNoWeb;
import com.alan.clients.module.impl.movement.noweb.IntaveNoWeb;
import com.alan.clients.module.impl.movement.noweb.PingNoWeb;
import com.alan.clients.module.setting.impl.ModeValue;

public class NoWeb extends Module {
    private final ModeValue mode;

    public NoWeb() {
        super("NoWeb", category.movement);
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new IgnoreNoWeb("Ignore", this))
                .add(new PingNoWeb("Ping", this))
                .add(new IntaveNoWeb("Intave", this))
                .add(new VulcanNoWeb("Vulcan", this))
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
