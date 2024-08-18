package com.alan.clients.module.impl.movement;

import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.ButtonSetting;

public class StopMotion extends Module {
    public static ButtonSetting a;
    public static ButtonSetting b;
    public static ButtonSetting c;

    public StopMotion() {
        super("Stop Motion", Module.category.movement, 0);
        this.registerSetting(a = new ButtonSetting("Stop X", true));
        this.registerSetting(b = new ButtonSetting("Stop Y", true));
        this.registerSetting(c = new ButtonSetting("Stop Z", true));
    }

    public void onEnable() {
        if (a.isToggled()) {
            mc.thePlayer.motionX = 0.0D;
        }

        if (b.isToggled()) {
            mc.thePlayer.motionY = 0.0D;
        }

        if (c.isToggled()) {
            mc.thePlayer.motionZ = 0.0D;
        }

        this.disable();
    }
}
