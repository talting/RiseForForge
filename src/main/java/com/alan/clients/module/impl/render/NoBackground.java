package com.alan.clients.module.impl.render;

import com.alan.clients.module.Module;
import com.alan.clients.module.ModuleManager;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.DescriptionSetting;
import com.alan.clients.utility.ContainerUtils;

public class NoBackground extends Module {
    private static ButtonSetting onlyChest = null;

    public NoBackground() {
        super("NoBackground", category.render);
        this.registerSetting(new DescriptionSetting("Remove default background."));
        this.registerSetting(onlyChest = new ButtonSetting("Only chest", false));
    }

    public static boolean noRender() {
        return ModuleManager.noBackground != null && ModuleManager.noBackground.isEnabled()
                && onlyChest != null && (!onlyChest.isToggled() || ContainerUtils.isChest(true));
    }
}
