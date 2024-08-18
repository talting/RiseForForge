package com.alan.clients.module.impl.other;

import com.alan.clients.Rise;
import com.alan.clients.event.ReceivePacketEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.impl.client.Notifications;
import com.alan.clients.module.setting.impl.ModeSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.utility.PacketUtils;
import com.alan.clients.utility.Utils;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class AutoRegister extends Module {
    private final ModeSetting message;
    private final SliderSetting length;
    private final SliderSetting minDelay;
    private final SliderSetting maxDelay;

    public AutoRegister() {
        super("AutoRegister", category.other);
        this.registerSetting(message = new ModeSetting("Message", new String[]{"/register <p>", "/register <p> <p>"}, 0));
        this.registerSetting(length = new SliderSetting("Length", 8, 4, 32, 1));
        this.registerSetting(minDelay = new SliderSetting("Min delay", 1500, 0, 5000, 500));
        this.registerSetting(maxDelay = new SliderSetting("Max delay", 3000, 0, 5000, 500));
    }

    @SubscribeEvent
    public void onReceivePacket(@NotNull ReceivePacketEvent event) {
        if (event.getPacket() instanceof S02PacketChat) {
            final String text = ((S02PacketChat) event.getPacket()).getChatComponent().getUnformattedText();

            if (text.contains("/register ")) {
                final long time = Utils.randomizeInt(minDelay.getInput(), maxDelay.getInput());
                final String pwd = RandomStringUtils.randomAscii((int) length.getInput());

                Notifications.sendNotification(Notifications.NotificationTypes.INFO, "Registering... (" + time + "ms)");
                Rise.getExecutor().schedule(() -> {
                    PacketUtils.sendPacket(new C01PacketChatMessage(message.getOptions()[(int) message.getInput()].replace("<p>", pwd)));
                    Notifications.sendNotification(Notifications.NotificationTypes.INFO, "Registered. (" + pwd + ")");
                }, time, TimeUnit.MILLISECONDS);
            }
        }
    }
}
