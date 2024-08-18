package com.alan.clients.module.impl.movement.jesus;

import com.alan.clients.event.BlockAABBEvent;
import com.alan.clients.event.MoveInputEvent;
import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.impl.movement.Jesus;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.BlockUtils;
import com.alan.clients.utility.Utils;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class KarhuJesus extends SubMode<Jesus> {
    public KarhuJesus(String name, @NotNull Jesus parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void onMoveInput(@NotNull MoveInputEvent event) {
        if (BlockUtils.blockRelativeToPlayer(0, -1, 0) instanceof BlockLiquid)
            event.setJump(false);
    }

    @SubscribeEvent
    public void onBlockAABB(@NotNull BlockAABBEvent event) {
        if (event.getBlock() instanceof BlockLiquid && !mc.gameSettings.keyBindSneak.isKeyDown()) {
            final int x = event.getBlockPos().getX();
            final int y = event.getBlockPos().getY();
            final int z = event.getBlockPos().getZ();

            event.setBoundingBox(AxisAlignedBB.fromBounds(x, y, z, x + 1, y + 1, z + 1));
        }
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (Utils.inLiquid()) {
            event.setPosY(event.getPosY() - (mc.thePlayer.ticksExisted % 2 == 0 ? 0.015625 : 0));
            event.setOnGround(false);
        }
    }
}
