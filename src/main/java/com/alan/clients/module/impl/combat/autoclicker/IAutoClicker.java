package com.alan.clients.module.impl.combat.autoclicker;

import com.alan.clients.module.Module;

public abstract class IAutoClicker extends Module {

    public IAutoClicker(String name, category moduleCategory) {
        super(name, moduleCategory);
    }

    public IAutoClicker(String name, category moduleCategory, String toolTip) {
        super(name, moduleCategory, toolTip);
    }

    public abstract boolean click();
}
