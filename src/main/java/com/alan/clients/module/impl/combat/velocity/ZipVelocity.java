package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.event.ReceivePacketEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.PacketUtils;
import com.alan.clients.utility.Utils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ZipVelocity extends SubMode<Velocity> {
    private final SliderSetting delay;
    private final ButtonSetting stopOnAttack;
    private final ButtonSetting debug;

    private long lastVelocityTime = -1;
    private boolean delayed = false;
    private final Queue<Packet<INetHandlerPlayClient>> delayedPackets = new ConcurrentLinkedQueue<>();

    public ZipVelocity(String name, @NotNull Velocity parent) {
        super(name, parent);
        this.registerSetting(delay = new SliderSetting("Max delay", 1000, 500, 10000, 250, "ms"));
        this.registerSetting(stopOnAttack = new ButtonSetting("Stop on attack", true));
        this.registerSetting(debug = new ButtonSetting("Debug", false));
    }

    @Override
    public void onDisable() {
        release();
    }

    private void release() {
        if (delayed) {
            if (debug.isToggled())
                Utils.sendMessage("release " + delayedPackets.size() + " packets.");

            for (Packet<INetHandlerPlayClient> p : delayedPackets) {
                PacketUtils.receivePacketNoEvent(p);
            }
        }

        delayed = false;
        lastVelocityTime = -1;
        delayedPackets.clear();
    }

    @SubscribeEvent
    public void onReceivePacket(@NotNull ReceivePacketEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            if (((S12PacketEntityVelocity) event.getPacket()).getEntityID() != mc.thePlayer.getEntityId()) return;

            event.setCanceled(true);
            delayedPackets.add(((S12PacketEntityVelocity) event.getPacket()));
            if (lastVelocityTime == -1) {
                lastVelocityTime = System.currentTimeMillis();
                delayed = true;
            }
        } else if (event.getPacket() instanceof S32PacketConfirmTransaction) {
            if (delayed) {
                if (System.currentTimeMillis() - lastVelocityTime >= (int) delay.getInput()) {
                    release();
                }
                event.setCanceled(true);
                delayedPackets.add(((S32PacketConfirmTransaction) event.getPacket()));
            }
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (delayed && stopOnAttack.isToggled())
            release();
    }
}
