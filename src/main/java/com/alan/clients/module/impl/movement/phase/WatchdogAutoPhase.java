package com.alan.clients.module.impl.movement.phase;

import com.alan.clients.event.BlockAABBEvent;
import com.alan.clients.event.PreUpdateEvent;
import com.alan.clients.event.ReceivePacketEvent;
import com.alan.clients.module.impl.movement.Phase;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.CoolDown;
import net.minecraft.block.BlockGlass;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import static com.alan.clients.module.ModuleManager.blink;

public class WatchdogAutoPhase extends SubMode<Phase> {
    private boolean phase;
    private final CoolDown stopWatch = new CoolDown(4000);

    public WatchdogAutoPhase(String name, Phase parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void onPreUpdate(PreUpdateEvent event) {
        if (phase && !stopWatch.hasFinished())
            blink.enable();
    }

    @SubscribeEvent
    public void onBlockAABBEvent(BlockAABBEvent event) {
        if (phase && event.getBlock() instanceof BlockGlass) event.setBoundingBox(null);
    }

    @SubscribeEvent
    public void onPacketReceiveEvent(@NotNull ReceivePacketEvent event) {
        Packet<?> packet = event.getPacket();

        if (packet instanceof S02PacketChat) {
            S02PacketChat s02PacketChat = ((S02PacketChat) packet);
            String chat = s02PacketChat.getChatComponent().getUnformattedText();

            switch (chat) {
                case "Cages opened! FIGHT!":
                case "§r§r§r                               §r§f§lSkyWars Duel§r":
                case "§r§eCages opened! §r§cFIGHT!§r":
                    phase = false;
                    blink.disable();
                    break;

                case "The game starts in 3 seconds!":
                case "§r§e§r§eThe game starts in §r§a§r§c3§r§e seconds!§r§e§r":
                case "§r§eCages open in: §r§c3 §r§eseconds!§r":
                    phase = true;
                    stopWatch.start();
                    break;
            }
        }
    }

    @Override
    public void onDisable() {
        blink.disable();
    }
}