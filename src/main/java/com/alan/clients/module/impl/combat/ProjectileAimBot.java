package com.alan.clients.module.impl.combat;

import com.alan.clients.event.PreUpdateEvent;
import com.alan.clients.module.ModuleManager;
import com.alan.clients.module.impl.combat.autoclicker.IAutoClicker;
import com.alan.clients.module.impl.combat.autoclicker.LowCPSAutoClicker;
import com.alan.clients.module.impl.combat.autoclicker.NormalAutoClicker;
import com.alan.clients.module.impl.combat.autoclicker.RecordAutoClicker;
import com.alan.clients.module.impl.other.SlotHandler;
import com.alan.clients.module.setting.impl.ModeValue;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.utility.ContainerUtils;
import com.alan.clients.utility.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ProjectileAimBot extends IAutoClicker {
    private final ModeValue clickMode;
    private final SliderSetting maxTargetLookFovDiff;

    private int fromSlot = -1;
    private boolean targeted = false;

    public ProjectileAimBot() {
        super("ProjectileAimBot", category.combat);
        this.registerSetting(clickMode = new ModeValue("Click mode", this)
                .add(new LowCPSAutoClicker("Normal", this, false, true))
                .add(new NormalAutoClicker("NormalFast", this, false, true))
                .add(new RecordAutoClicker("Record", this, false, true))
        );
        this.registerSetting(maxTargetLookFovDiff = new SliderSetting("MaxTargetLookFovDiff", 180, 20, 180, 1));

    }

    @Override
    public void onEnable() {
        clickMode.enable();
    }

    @Override
    public void onDisable() {
        clickMode.disable();
    }

    @SubscribeEvent
    public void onPreUpdate(PreUpdateEvent event) {
        if (KillAura.target != null && !ModuleManager.killAura.isAttack() && Utils.inFov((float) maxTargetLookFovDiff.getInput(), KillAura.target)) {
            int slot = ContainerUtils.getMostProjectiles(-1);
            if (slot == -1) return;
            if (slot >= 9)
                slot -= 36;
            if (fromSlot == -1)
                fromSlot = SlotHandler.getCurrentSlot();
            SlotHandler.setCurrentSlot(slot);
            targeted = true;
        }
    }

    @Override
    public boolean click() {
        if (ContainerUtils.isProjectiles(SlotHandler.getHeldItem()) && targeted) {
            Utils.sendClick(1, true);
            targeted = false;
            Utils.sendClick(1, false);
            return true;
        } else {
            if (fromSlot != -1) {
                SlotHandler.setCurrentSlot(fromSlot);
                fromSlot = -1;
            }
            return false;
        }
    }
}
