package com.alan.clients.module.impl.movement;

import com.alan.clients.module.Module;
import com.alan.clients.module.impl.movement.jesus.HypixelJesus;
import com.alan.clients.module.impl.movement.jesus.KarhuJesus;
import com.alan.clients.module.impl.movement.jesus.OldNCPJesus;
import com.alan.clients.module.impl.movement.jesus.VulcanJesus;
import com.alan.clients.module.setting.impl.ModeValue;

public class Jesus extends Module {
    private final ModeValue mode;

    public Jesus() {
        super("Jesus", category.movement);
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new KarhuJesus("Karhu", this))
                .add(new OldNCPJesus("Old NCP", this))
                .add(new VulcanJesus("Vulcan", this))
                .add(new HypixelJesus("Hypixel", this))
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
