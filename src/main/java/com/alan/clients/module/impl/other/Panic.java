package com.alan.clients.module.impl.other;

import com.alan.clients.Rise;
import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.DescriptionSetting;
import java.util.ArrayList;
import java.util.List;

public class Panic extends Module {
    public Panic() {
        super("Panic", category.other);
        this.registerSetting(new DescriptionSetting("Disables all modules."));
    }

    @Override
    public void onEnable() {
        List<Module> modulesToDisable = new ArrayList<>();
        for (Module m : Rise.getModuleManager().getModules()) {
            if (m.isEnabled()) {
                modulesToDisable.add(m);
            }
        }
        for (Module m : modulesToDisable) {
            m.disable();

        }
        this.disable();
    }
}
