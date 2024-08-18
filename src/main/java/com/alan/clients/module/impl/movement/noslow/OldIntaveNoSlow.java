package com.alan.clients.module.impl.movement.noslow;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.impl.movement.NoSlow;
import com.alan.clients.module.impl.other.SlotHandler;
import com.alan.clients.utility.PacketUtils;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class OldIntaveNoSlow extends INoSlow {
    public OldIntaveNoSlow(String name, @NotNull NoSlow parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (mc.thePlayer.isUsingItem()) {
            PacketUtils.sendPacket(new C09PacketHeldItemChange(SlotHandler.getCurrentSlot() % 8 + 1));
            PacketUtils.sendPacket(new C09PacketHeldItemChange(SlotHandler.getCurrentSlot()));
        }
    }

    @Override
    public float getSlowdown() {
        return 1;
    }
}
