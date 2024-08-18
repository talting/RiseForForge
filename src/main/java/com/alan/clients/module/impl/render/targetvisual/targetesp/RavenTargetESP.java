package com.alan.clients.module.impl.render.targetvisual.targetesp;

import com.alan.clients.module.impl.render.TargetESP;
import com.alan.clients.module.impl.render.targetvisual.ITargetVisual;
import com.alan.clients.module.setting.impl.ModeSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.Theme;
import com.alan.clients.utility.render.RenderUtils;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;

public class RavenTargetESP extends SubMode<TargetESP> implements ITargetVisual {
    private final ModeSetting theme;

    public RavenTargetESP(String name, @NotNull TargetESP parent) {
        super(name, parent);
        this.registerSetting(theme = new ModeSetting("Theme", Theme.themes, 0));
    }

    @Override
    public void render(@NotNull EntityLivingBase target) {
        RenderUtils.renderEntity(target, 2, 0.0, 0.0, Theme.getGradient((int) theme.getInput(), 0), target.hurtTime != 0);
    }
}
