package com.alan.clients.module.impl.render;

import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.DescriptionSetting;
import com.alan.clients.module.setting.impl.SliderSetting;

import static com.alan.clients.module.ModuleManager.particles;

public class Particles extends Module {
    private final SliderSetting multiplier;
    private final ButtonSetting alwaysSharpness;
    private final ButtonSetting alwaysCriticals;

    public Particles() {
        super("Particles", category.render);
        this.registerSetting(new DescriptionSetting("modify the particles on attack."));
        this.registerSetting(multiplier = new SliderSetting("Multiplier", 2, 0, 10, 1));
        this.registerSetting(alwaysSharpness = new ButtonSetting("Always sharpness", false));
        this.registerSetting(alwaysCriticals = new ButtonSetting("Always criticals", false));
    }

    public static int getCriticalsMultiplier(boolean should) {
        if (particles == null || !particles.isEnabled()) return should ? 1 : 0;
        if (particles.multiplier.getInput() == 0) return 0;
        if (!should && !particles.alwaysCriticals.isToggled()) return 0;

        return (int) particles.multiplier.getInput();
    }

    public static int getSharpnessMultiplier(boolean should) {
        if (particles == null || !particles.isEnabled()) return should ? 1 : 0;
        if (particles.multiplier.getInput() == 0) return 0;
        if (!should && !particles.alwaysSharpness.isToggled()) return 0;

        return (int) particles.multiplier.getInput();
    }
}
