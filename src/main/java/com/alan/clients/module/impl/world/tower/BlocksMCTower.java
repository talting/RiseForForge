package com.alan.clients.module.impl.world.tower;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.event.SendPacketEvent;
import com.alan.clients.module.impl.world.Tower;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import static com.alan.clients.module.ModuleManager.tower;

public class BlocksMCTower extends SubMode<Tower> {
    private final SliderSetting speed;

    public BlocksMCTower(String name, @NotNull Tower parent) {
        super(name, parent);
        this.registerSetting(speed = new SliderSetting("Speed", 0.95, 0.5, 1, 0.01));
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (tower.canTower()) {
            if (mc.thePlayer.onGround)
                mc.thePlayer.motionY = 0.42F;
            mc.thePlayer.motionX *= speed.getInput();
            mc.thePlayer.motionZ *= speed.getInput();
        }
    }

    @SubscribeEvent
    public void onSendPacket(SendPacketEvent event) {
        if (tower.canTower()) {
            if (mc.thePlayer.motionY > -0.0784000015258789 && event.getPacket() instanceof C08PacketPlayerBlockPlacement) {
                final C08PacketPlayerBlockPlacement wrapper = ((C08PacketPlayerBlockPlacement) event.getPacket());

                if (wrapper.getPosition().equals(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.4, mc.thePlayer.posZ))) {
                    mc.thePlayer.motionY = -0.0784000015258789;
                }
            }
        }
    }
}
