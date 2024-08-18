package com.alan.clients.module.impl.movement.jesus;

import com.alan.clients.event.BlockAABBEvent;
import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.event.PrePlayerInputEvent;
import com.alan.clients.event.ReceivePacketEvent;
import com.alan.clients.module.impl.movement.Jesus;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.Utils;
import net.minecraft.block.BlockLiquid;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Skidded from Rise (com.alan.clients.module.impl.movement.jesus.WatchdogJesus)
 * <p>
 * Counter-confused by 夏翊淳
 * @see hackclient.rise.lk
 * @author Alan34
 */
public class HypixelJesus extends SubMode<Jesus> {
    private Boolean tW = false;

    public HypixelJesus(String name, @NotNull Jesus parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void tX(@NotNull BlockAABBEvent var1x) {
        if (var1x.getBlock() instanceof BlockLiquid) {
            this.tW = true;
            int var2 = var1x.getBlockPos().getX();
            int var3 = var1x.getBlockPos().getY();
            int var4 = var1x.getBlockPos().getZ();
            var1x.setBoundingBox(AxisAlignedBB.fromBounds(var2, var3, var4, var2 + 1, var3 + 1, var4 + 1));
        } else {
            this.tW = false;
        }
    }

    @SubscribeEvent
    public void tY(PreMotionEvent var0) {
        if (Utils.inLiquid()) {
            var0.setPosY(var0.getPosY() - (mc.thePlayer.ticksExisted % 2 == 0 ? 0.15625 : 0.10625));
            var0.setOnGround(false);
        }
    }

    @SubscribeEvent
    public void tZ(PrePlayerInputEvent var0) {
        if (Utils.inLiquid()) {
            var0.setSpeed(0.1527);
        }
    }

    @SubscribeEvent
    public void ua(@NotNull ReceivePacketEvent var1x) {
        Packet<?> var2 = var1x.getPacket();
        if (((var2 instanceof S12PacketEntityVelocity) && this.tW) || ((var2 instanceof S12PacketEntityVelocity) && Utils.inLiquid())) {
            S12PacketEntityVelocity var3 = (S12PacketEntityVelocity) var2;
            if (var3.getEntityID() == mc.thePlayer.getEntityId()) {
                var1x.setCanceled(true);
            }
        }
    }
}
