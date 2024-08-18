package com.alan.clients.module.impl.render;

import com.alan.clients.Rise;
import com.alan.clients.module.Module;
import com.alan.clients.module.impl.world.AntiBot;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.utility.render.RenderUtils;
import com.alan.clients.utility.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.Iterator;

public class Tracers extends Module {
    public ButtonSetting a;
    public SliderSetting b;
    public SliderSetting c;
    public SliderSetting d;
    public ButtonSetting e;
    public SliderSetting f;
    private boolean g;
    private int rgb_c = 0;

    public Tracers() {
        super("Tracers", Module.category.render, 0);
        this.registerSetting(a = new ButtonSetting("Show invis", true));
        this.registerSetting(f = new SliderSetting("Line Width", 1.0D, 1.0D, 5.0D, 1.0D));
        this.registerSetting(b = new SliderSetting("Red", 0.0D, 0.0D, 255.0D, 1.0D));
        this.registerSetting(c = new SliderSetting("Green", 255.0D, 0.0D, 255.0D, 1.0D));
        this.registerSetting(d = new SliderSetting("Blue", 0.0D, 0.0D, 255.0D, 1.0D));
        this.registerSetting(e = new ButtonSetting("Rainbow", false));
    }

    public void onEnable() {
        this.g = mc.gameSettings.viewBobbing;
        if (this.g) {
            mc.gameSettings.viewBobbing = false;
        }

    }

    public void onDisable() {
        mc.gameSettings.viewBobbing = this.g;
    }

    public void onUpdate() {
        if (mc.gameSettings.viewBobbing) {
            mc.gameSettings.viewBobbing = false;
        }

    }

    public void guiUpdate() {
        this.rgb_c = (new Color((int) b.getInput(), (int) c.getInput(), (int) d.getInput())).getRGB();
    }

    @SubscribeEvent
    public void o(RenderWorldLastEvent ev) {
        if (Utils.nullCheck()) {
            int rgb = e.isToggled() ? Utils.getChroma(2L, 0L) : this.rgb_c;
            Iterator var3;
            if (Rise.debugger) {
                var3 = mc.theWorld.loadedEntityList.iterator();

                while (var3.hasNext()) {
                    Entity en = (Entity) var3.next();
                    if (en instanceof EntityLivingBase && en != mc.thePlayer) {
                        RenderUtils.dtl(en, rgb, (float) f.getInput());
                    }
                }

            } else {
                var3 = mc.theWorld.playerEntities.iterator();

                while (true) {
                    EntityPlayer en;
                    do {
                        do {
                            do {
                                if (!var3.hasNext()) {
                                    return;
                                }

                                en = (EntityPlayer) var3.next();
                            } while (en == mc.thePlayer);
                        } while (en.deathTime != 0);
                    } while (!a.isToggled() && en.isInvisible());

                    if (!AntiBot.isBot(en)) {
                        RenderUtils.dtl(en, rgb, (float) f.getInput());
                    }
                }
            }
        }
    }
}
