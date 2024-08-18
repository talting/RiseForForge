package com.alan.clients.module.impl.movement.wallclimb;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.impl.movement.WallClimb;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.MoveUtil;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class VulcanWallClimb extends SubMode<WallClimb> {
    public VulcanWallClimb(String name, @NotNull WallClimb parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (mc.thePlayer.isCollidedHorizontally) {
            if (mc.thePlayer.ticksExisted % 2 == 0) {
                event.setOnGround(true);
                mc.thePlayer.motionY = MoveUtil.jumpMotion();
            }

            final double yaw = MoveUtil.direction();
            event.setPosX(event.getPosX() - -MathHelper.sin((float) yaw) * 0.1f);
            event.setPosZ(event.getPosZ() - MathHelper.cos((float) yaw) * 0.1f);
        }
    }
}
