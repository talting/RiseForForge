package com.alan.clients.module.impl.client;

import com.alan.clients.Rise;
import com.alan.clients.clickgui.ClickGui;
import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.ModeSetting;
import com.alan.clients.utility.Utils;

public class Gui extends Module {
    public static ButtonSetting removePlayerModel, resetPosition, translucentBackground, removeWatermark, rainBowOutlines, toolTip;
    public static ModeSetting font;

    public Gui() {
        super("Gui", Module.category.client, 54);
        this.registerSetting(rainBowOutlines = new ButtonSetting("Rainbow outlines", true));
        this.registerSetting(removePlayerModel = new ButtonSetting("Remove player model", false));
        this.registerSetting(removeWatermark = new ButtonSetting("Remove watermark", false));
        this.registerSetting(translucentBackground = new ButtonSetting("Translucent background", true));
        this.registerSetting(toolTip = new ButtonSetting("Tool tip", true));
        this.registerSetting(resetPosition = new ButtonSetting("Reset position", ClickGui::resetPosition));
        this.registerSetting(font = new ModeSetting("Font", new String[]{"Minecraft", "Product Sans", "Tenacity"}, 0));
    }

    public void onEnable() {
        if (Utils.nullCheck() && mc.currentScreen != Rise.clickGui) {
            mc.displayGuiScreen(Rise.clickGui);
            Rise.clickGui.initMain();
        }
        this.disable();
    }
}
