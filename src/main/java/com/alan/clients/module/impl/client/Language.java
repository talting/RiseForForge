package com.alan.clients.module.impl.client;

import com.alan.clients.Rise;
import com.alan.clients.module.Module;
import com.alan.clients.module.ModuleManager;
import com.alan.clients.module.setting.impl.ModeValue;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.i18n.I18nManager;
import com.alan.clients.utility.i18n.I18nModule;

import java.util.Map;

public class Language extends Module {
    public final ModeValue mode;

    public Language() {
        super("Language", category.client);
        mode = new ModeValue("Language", this);

        String[] languageList = I18nManager.LANGUAGE_LIST;
        for (int i = 0; i < languageList.length; i++) {
            final String s = languageList[i];
            final int finalI = i;
            mode.add(new SubMode<Module>(s, this) {
                @Override
                public void onEnable() {
                    Map<Module, I18nModule> map = I18nManager.MODULE_MAP.get(finalI);
                    for (Module module : Rise.getModuleManager().getModules()) {
                        module.setI18nObject(map.getOrDefault(module, null));
                    }
                    ModuleManager.sort();
                }
            });
        }

        this.registerSetting(mode);
    }

    @Override
    public void onEnable() {
        mode.enable();
    }

    @Override
    public void onDisable() {
        mode.disable();

        for (Module module : Rise.getModuleManager().getModules()) {
            module.setI18nObject(null);
        }
        ModuleManager.sort();
    }
}
