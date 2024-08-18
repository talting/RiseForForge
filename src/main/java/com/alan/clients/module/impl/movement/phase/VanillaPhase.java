package com.alan.clients.module.impl.movement.phase;

import com.alan.clients.event.BlockAABBEvent;
import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.impl.movement.Phase;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.BlockUtils;
import com.alan.clients.utility.PacketUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class VanillaPhase extends SubMode<Phase> {

    private boolean phasing;

    public VanillaPhase(String name, Phase parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void onPreMotionEvent(PreMotionEvent event) {
        this.phasing = false;

        final double rotation = Math.toRadians(mc.thePlayer.rotationYaw);

        final double x = Math.sin(rotation);
        final double z = Math.cos(rotation);

        if (mc.thePlayer.isCollidedHorizontally) {
            mc.thePlayer.setPosition(mc.thePlayer.posX - x * 0.005, mc.thePlayer.posY, mc.thePlayer.posZ + z * 0.005);
            this.phasing = true;
        } else if (BlockUtils.insideBlock()) {
            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX - x * 1.5, mc.thePlayer.posY, mc.thePlayer.posZ + z * 1.5, false));

            mc.thePlayer.motionX *= 0.3D;
            mc.thePlayer.motionZ *= 0.3D;

            this.phasing = true;
        }
    }

    @SubscribeEvent
    public void onBlockAABB(@NotNull BlockAABBEvent event) {
        // Sets The Bounding Box To The Players Y Position.
        if (event.getBlock() instanceof BlockAir && phasing) {
            final double x = event.getBlockPos().getX(), y = event.getBlockPos().getY(), z = event.getBlockPos().getZ();

            if (y < mc.thePlayer.posY) {
                event.setBoundingBox(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
            }
        }
    }
}