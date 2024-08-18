package com.alan.clients.module.impl.movement.noslow;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.ModuleManager;
import com.alan.clients.module.impl.movement.NoSlow;
import com.alan.clients.module.impl.other.SlotHandler;
import com.alan.clients.utility.ContainerUtils;
import com.alan.clients.utility.MoveUtil;
import com.alan.clients.utility.PacketUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class IntaveNoSlow extends INoSlow {
    private boolean lastUsingItem = false;

    public IntaveNoSlow(String name, @NotNull NoSlow parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (!mc.thePlayer.isUsingItem() || SlotHandler.getHeldItem() == null) {
            lastUsingItem = false;
            return;
        }

        Item item = SlotHandler.getHeldItem().getItem();

        if (!MoveUtil.isMoving()) return;
        if (ContainerUtils.isRest(item)) {
            if (!lastUsingItem) {
                PacketUtils.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
            }
        } else {
            if (item instanceof ItemSword) {
                PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(SlotHandler.getHeldItem()));
            }
        }

        lastUsingItem = true;
    }

    @Override
    public void onDisable() {
        lastUsingItem = false;
    }

    @Override
    public float getSlowdown() {
        return 1;
    }
}
