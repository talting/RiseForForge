package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.event.PostVelocityEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class MatrixVelocity extends SubMode<Velocity> {
    private final ButtonSetting debug;

    private boolean reduced = false;

    public MatrixVelocity(String name, @NotNull Velocity parent) {
        super(name, parent);
        this.registerSetting(debug = new ButtonSetting("Debug", false));
    }

    @Override
    public void onEnable() {
        reduced = false;
    }

    @SubscribeEvent
    public void onPostVelocity(PostVelocityEvent event) {
        reduced = false;
    }

    @SubscribeEvent
    public void onAttack(@NotNull AttackEntityEvent event) {
        if (event.target instanceof EntityLivingBase && mc.thePlayer.hurtTime > 0) {
            if (reduced) return;

            if (mc.thePlayer.isSprinting()) {
                final double motionX = mc.thePlayer.motionX;
                final double motionZ = mc.thePlayer.motionZ;

                if (Math.abs(motionX) < 0.625 && Math.abs(motionZ) < 0.625) {
                    mc.thePlayer.motionX = motionX * 0.4;
                    mc.thePlayer.motionZ = motionZ * 0.4;
                } else if (Math.abs(motionX) < 1.25 && Math.abs(motionZ) < 1.25) {
                    mc.thePlayer.motionX = motionX * 0.67;
                    mc.thePlayer.motionZ = motionZ * 0.67;
                }
                mc.thePlayer.setSprinting(false);

                if (debug.isToggled())
                    Utils.sendMessage(String.format("reduced %.2f %.2f", motionX - mc.thePlayer.motionX, motionZ - mc.thePlayer.motionZ));
            }
            reduced = true;
        }
    }
}
