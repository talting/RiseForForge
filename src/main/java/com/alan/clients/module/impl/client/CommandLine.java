package com.alan.clients.module.impl.client;

import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.utility.Commands;
import com.alan.clients.utility.Timer;

public class CommandLine extends Module {
    public static boolean a = false;
    public static boolean b = false;
    public static Timer an;
    public static ButtonSetting animate;

    public CommandLine() {
        super("Command line", Module.category.client, 0);
        this.registerSetting(animate = new ButtonSetting("Animate", true));
    }

    public void onEnable() {
        Commands.setccs();
        a = true;
        b = false;
        (an = new Timer(500.0F)).start();
    }

    public void onDisable() {
        b = true;
        if (an != null) {
            an.start();
        }

        Commands.od();
    }
}
