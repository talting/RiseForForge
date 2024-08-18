package com.alan.clients.module.impl.movement.fly;

import com.alan.clients.event.MoveEvent;
import com.alan.clients.event.PreVelocityEvent;
import com.alan.clients.event.ReceivePacketEvent;
import com.alan.clients.event.RotationEvent;
import com.alan.clients.module.impl.movement.Fly;
import com.alan.clients.module.impl.other.RotationHandler;
import com.alan.clients.module.impl.other.SlotHandler;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.ContainerUtils;
import com.alan.clients.utility.MoveUtil;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class MatrixBowFly extends SubMode<Fly> {
    private float yaw;

    public MatrixBowFly(String name, @NotNull Fly parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void onRotation(@NotNull RotationEvent event) {
        event.setPitch(-85);
        event.setYaw(yaw + 180);
    }

    @Override
    public void onUpdate() {
        SlotHandler.setCurrentSlot(ContainerUtils.getSlot(ItemBow.class));
    }

    @SubscribeEvent
    public void onPreVelocity(@NotNull PreVelocityEvent event) {
        event.setCanceled(true);
        yaw = mc.thePlayer.rotationYaw;  // because we have set the rotation yaw on RotationEvent.
        mc.thePlayer.motionY = Math.abs(event.getMotionY() / 8000);
        MoveUtil.strafe(Math.hypot(event.getMotionX() / 8000.0, event.getMotionZ() / 8000.0));
    }

    @SubscribeEvent
    public void onMove(@NotNull MoveEvent event) {
        if (mc.thePlayer.hurtTime == 0) {
            event.setCanceled(true);
            mc.thePlayer.motionY = 0;
        }
    }

    @Override
    public void onEnable() {
        yaw = RotationHandler.getRotationYaw();
    }
}
