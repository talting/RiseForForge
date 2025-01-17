package com.alan.clients.module.impl.movement.noslow;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.event.SendPacketEvent;
import com.alan.clients.module.impl.movement.NoSlow;
import com.alan.clients.module.impl.other.SlotHandler;
import com.alan.clients.utility.ContainerUtils;
import com.alan.clients.utility.PacketUtils;
import com.alan.clients.utility.Utils;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HypixelNoSlow extends INoSlow {
    private int offGroundTicks = 0;
    private boolean send = false;

    public HypixelNoSlow(String name, @NotNull NoSlow parent) {
        super(name, parent);
    }

    @Override
    public void onUpdate() {
        if (!mc.thePlayer.isUsingItem() || SlotHandler.getHeldItem() == null) return;

        if (SlotHandler.getHeldItem().getItem() instanceof ItemSword) {
            PacketUtils.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 7 + (int) (Math.random() * 2.0) + 1));
            PacketUtils.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        }
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (mc.thePlayer.onGround) {
            offGroundTicks = 0;
        } else {
            offGroundTicks++;
        }

        final @Nullable ItemStack item = SlotHandler.getHeldItem();
        if (offGroundTicks == 4 && send) {
            send = false;
            PacketUtils.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(
                    new BlockPos(-1, -1, -1),
                    255, item,
                    0, 0, 0
            ));

        } else if (item != null && mc.thePlayer.isUsingItem()) {
            event.setPosY(event.getPosY() + 1E-14);
        }
    }

    @SubscribeEvent
    public void onSendPacket(@NotNull SendPacketEvent event) {
        if (event.getPacket() instanceof C08PacketPlayerBlockPlacement && !mc.thePlayer.isUsingItem()) {
            C08PacketPlayerBlockPlacement blockPlacement = (C08PacketPlayerBlockPlacement) event.getPacket();
            if (SlotHandler.getHeldItem() != null && blockPlacement.getPlacedBlockDirection() == 255
                    && ContainerUtils.isRest(SlotHandler.getHeldItem().getItem()) && offGroundTicks < 2) {
                if (mc.thePlayer.onGround && !Utils.jumpDown()) {
                    mc.thePlayer.jump();
                }
                send = true;
                event.setCanceled(true);
            }
        }
    }

    @Override
    public float getSlowdown() {
        ItemStack item = SlotHandler.getHeldItem();
        if (item == null) return 1;
        if (item.getItem() instanceof ItemPotion) return .8f;
        return 1;
    }
}
