package com.alan.clients.module.impl.render;

import com.alan.clients.module.Module;
import com.alan.clients.module.impl.combat.KillAura;
import com.alan.clients.module.impl.render.targetvisual.ITargetVisual;
import com.alan.clients.module.impl.render.targetvisual.targetesp.JelloTargetESP;
import com.alan.clients.module.impl.render.targetvisual.targetesp.RavenTargetESP;
import com.alan.clients.module.impl.render.targetvisual.targetesp.VapeTargetESP;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.ModeValue;
import com.alan.clients.utility.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

public class TargetESP extends Module {
    private final ModeValue mode;
    private final ButtonSetting onlyKillAura;

    private static @Nullable EntityLivingBase target = null;
    private long lastTargetTime = -1;

    public TargetESP() {
        super("TargetESP", category.render);
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new RavenTargetESP("Rise", this))
                .add(new JelloTargetESP("Jello", this))
                .add(new VapeTargetESP("Vape", this))
        );
        this.registerSetting(onlyKillAura = new ButtonSetting("Only killAura", true));
    }

    @Override
    public void onEnable() {
        mode.enable();
    }

    @Override
    public void onDisable() {
        mode.disable();

        target = null;
        lastTargetTime = -1;
    }

    @Override
    public void onUpdate() {
        if (!Utils.nullCheck()) {
            target = null;
            return;
        }


        if (KillAura.target != null) {
            target = KillAura.target;
            lastTargetTime = System.currentTimeMillis();
        }

        if (target != null && lastTargetTime != -1 && (System.currentTimeMillis() - lastTargetTime > 1000 || target.getDistanceSqToEntity(mc.thePlayer) > 10)) {
            target = null;
            lastTargetTime = -1;
        }

        if (onlyKillAura.isToggled()) return;

        // manual target
        if (target != null) {
            if (!Utils.inFov(180, target) || target.getDistanceSqToEntity(mc.thePlayer) > 36) {
                target = null;
            }
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (onlyKillAura.isToggled()) return;

        if (event.target instanceof EntityLivingBase) {
            target = (EntityLivingBase) event.target;
        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (target != null)
            ((ITargetVisual) mode.getSubModeValues().get((int) mode.getInput())).render(target);
    }
}
