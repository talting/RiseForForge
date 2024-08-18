package com.alan.clients.module.impl.movement.wallclimb;

import com.alan.clients.event.BlockAABBEvent;
import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.event.PushOutOfBlockEvent;
import com.alan.clients.mixins.impl.client.KeyBindingAccessor;
import com.alan.clients.module.impl.movement.WallClimb;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.BlockUtils;
import com.alan.clients.utility.MoveUtil;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class TestWallClimb extends SubMode<WallClimb> {
    public TestWallClimb(String name, @NotNull WallClimb parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (mc.thePlayer.isCollidedHorizontally && !BlockUtils.insideBlock()) {
            double yaw = MoveUtil.direction();
            mc.thePlayer.setPosition(
                    mc.thePlayer.posX + -MathHelper.sin((float) yaw) * 0.05,
                    mc.thePlayer.posY,
                    mc.thePlayer.posZ + MathHelper.cos((float) yaw) * 0.05
            );
            MoveUtil.stop();
            ((KeyBindingAccessor) mc.gameSettings.keyBindForward).setPressed(false);
        }
    }

    @SubscribeEvent
    public void onPushOutOfBlock(@NotNull PushOutOfBlockEvent event) {
        if (BlockUtils.insideBlock())
            event.setCanceled(true);
    }

    @SubscribeEvent
    public void onAABB(BlockAABBEvent event) {
        if (BlockUtils.insideBlock()) {
            BlockPos playerPos = new BlockPos(mc.thePlayer);
            BlockPos blockPos = event.getBlockPos();
            if (blockPos.getY() > playerPos.getY())
                event.setBoundingBox(null);
        }
    }
}
