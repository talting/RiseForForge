package com.alan.clients.module.impl.movement.fly;

import com.alan.clients.event.BlockAABBEvent;
import com.alan.clients.module.impl.movement.Fly;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.BlockUtils;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class AirWalkFly extends SubMode<Fly> {
    public AirWalkFly(String name, @NotNull Fly parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void onBlockAABB(@NotNull BlockAABBEvent event) {
        if (BlockUtils.replaceable(event.getBlockPos())) {
            final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

            if (y < mc.thePlayer.posY) {
                event.setBoundingBox(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
            }
        }
    }
}
