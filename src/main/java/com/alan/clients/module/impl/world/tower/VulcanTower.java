package com.alan.clients.module.impl.world.tower;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.impl.world.Tower;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.MoveUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class VulcanTower extends SubMode<Tower> {
    public VulcanTower(String name, @NotNull Tower parent) {
        super(name, parent);
    }

    @Override
    public void onUpdate() {
        if (parent.canTower()) {
            mc.thePlayer.motionY = mc.thePlayer.ticksExisted % 2 == 0 ? 0.7 : MoveUtil.isMoving() ? 0.42 : 0.6;
        }
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (mc.thePlayer.ticksExisted % 2 == 0 && !MoveUtil.isMoving()) {
            event.setPosX(event.getPosX() + 0.1);
            event.setPosZ(event.getPosZ() + 0.1);
        }
    }
}
