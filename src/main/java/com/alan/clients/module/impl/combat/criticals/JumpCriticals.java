package com.alan.clients.module.impl.combat.criticals;

import com.alan.clients.module.impl.combat.Criticals;
import com.alan.clients.module.setting.impl.SubMode;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class JumpCriticals extends SubMode<Criticals> {
    public JumpCriticals(String name, @NotNull Criticals parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void onAttack(@NotNull AttackEntityEvent event) {
        if (event.target instanceof EntityLivingBase && mc.thePlayer.onGround)
            mc.thePlayer.jump();
    }
}
