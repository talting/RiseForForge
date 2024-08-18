package com.alan.clients.module.impl.render;

import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.DescriptionSetting;

public class AntiShuffle extends Module {
    public static DescriptionSetting a;
    private static String c = "§k";

    public AntiShuffle() {
        super("AntiShuffle", Module.category.render, 0);
        this.registerSetting(a = new DescriptionSetting("Removes obfuscation (" + c + "hey" + "§" + "r)."));
    }

    public static String removeObfuscation(String s) {
        return s.replace(c, "");
    }
}
