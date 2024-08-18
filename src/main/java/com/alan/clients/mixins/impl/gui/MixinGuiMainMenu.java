package com.alan.clients.mixins.impl.gui;

import com.alan.clients.Rise;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.alan.clients.module.ModuleManager;
import com.alan.clients.utility.font.FontManager;
import com.alan.clients.utility.render.BackgroundUtils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Mixin(value = GuiMainMenu.class, priority = 1983)
public abstract class MixinGuiMainMenu extends GuiScreen {
    @Unique
    private static final int LOGO_COLOR = new Color(255, 255, 255, 200).getRGB();

    @Shadow private int field_92022_t;

    @Shadow protected abstract boolean func_183501_a();

    @Shadow private GuiScreen field_183503_M;

    @Shadow private String splashText;

    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    public void onDrawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_, CallbackInfo ci) {
        if (!ModuleManager.clientTheme.isEnabled() || !ModuleManager.clientTheme.mainMenu.isToggled())
            return;

        BackgroundUtils.renderBackground(this);

        FontManager.tenacity80.drawCenteredString("Rise", width / 2.0, height * 0.2, LOGO_COLOR);

        List<String> branding = Lists.reverse(FMLCommonHandler.instance().getBrandings(true));

        for(int breadline = 0; breadline < branding.size(); ++breadline) {
            String brd = branding.get(breadline);
            if (!Strings.isNullOrEmpty(brd)) {
                this.drawString(this.fontRendererObj, brd, 2, this.height - (10 + breadline * (this.fontRendererObj.FONT_HEIGHT + 1)), 16777215);
            }
        }

        ForgeHooksClient.renderMainMenu((GuiMainMenu) (Object) this, this.fontRendererObj, this.width, this.height);
        String s1 = "MainMenu skidded from Xylitol Client.";
        this.drawString(this.fontRendererObj, s1, this.width - this.fontRendererObj.getStringWidth(s1) - 2, this.height - 10, -1);
        String s2 = Rise.moduleCounter + " modules and " + Rise.settingCounter + " settings loaded!";
        this.drawString(this.fontRendererObj, s2, this.width - this.fontRendererObj.getStringWidth(s2) - 2, 2, -1);

        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        if (this.func_183501_a()) {
            this.field_183503_M.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        }

        ci.cancel();
    }

    @Inject(method = "initGui", at = @At("HEAD"), cancellable = true)
    public void onInitGui(CallbackInfo ci) {
        if (!ModuleManager.clientTheme.isEnabled() || !ModuleManager.clientTheme.mainMenu.isToggled())
            return;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int day = calendar.get(Calendar.DATE);
        if (month == 12 && day == 24) {
            this.splashText = "Merry X-mas!";
        } else if (month == 1 && day == 1) {
            this.splashText = "Happy new year!";
        } else if (month == 6 && day == 5) {
            this.splashText = "Rise was born today!";
        }

        int j = this.height / 4 + 48;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 103, j, 200, 18, "SinglePlayer"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 103, j + 24, 200, 18, "MultiPlayer"));
        this.buttonList.add(new GuiButton(6, this.width / 2 - 103, j + 48, 200, 18, "Mods"));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 103, j + 72 + 12, 98, 18, "Options"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 1, j + 72 + 12, 98, 18, "Quit"));

        this.mc.setConnectedToRealms(false);
        ci.cancel();
    }
}
