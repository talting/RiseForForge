package com.alan.clients.module.impl.other.anticheats.utils.alert;

import com.alan.clients.Rise;
import com.alan.clients.module.impl.other.Anticheat;
import com.alan.clients.utility.Utils;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
    public static final Logger LOGGER = LogManager.getLogger("AntiCheat");

    public static void alert(String player, String module, String extraMsg) {
        if (check()) {
            final ChatComponentText chatComponentText = new ChatComponentText(String.format("%s§r §r%s§r %s§r §b%s§r | %s§r", "§b§lTR§r§l>", player, "failed", module, extraMsg));
            final ChatStyle chatStyle = new ChatStyle();
            chatStyle.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wdr " + player));
            ((IChatComponent)chatComponentText).appendSibling(new ChatComponentText(Utils.formatColor(" §7[§cWDR§7]")).setChatStyle(chatStyle));
            Rise.mc.thePlayer.addChatMessage(chatComponentText);
            if (Anticheat.getShouldPing().isToggled()) {
                switch ((int) Anticheat.getPingSound().getInput()) {
                    case 0:
                        Rise.mc.thePlayer.playSound("note.pling", 1, 1);
                        break;
                    case 1:
                        Rise.mc.thePlayer.playSound("rise:alarm", 1, 1);
                        break;
                }

            }
            if (Anticheat.getAutoReport().getInput() != 0) {
                Rise.mc.thePlayer.sendChatMessage(
                        Anticheat.getAutoReport().getOptions()[(int) Anticheat.getAutoReport().getInput()]
                                + " "
                                + Utils.stripColor(player)
                );
            }
        }
    }

    public static void prefix(String prefix, String msg) {
        if (check()) {
            Rise.mc.thePlayer.addChatMessage(new ChatComponentText(
                    String.format("%s§r §r%s§r | %s§r", "§b§lTR§r§l>", prefix, msg)
            ));
        }
    }

    public static void custom(String msg) {
        if (check()) {
            Rise.mc.thePlayer.addChatMessage(new ChatComponentText(
                    String.format("%s§r §r%s§r", "§b§lTR§r§l>", msg)
            ));
        }


    }

    private static boolean check() {
        return Rise.mc.thePlayer != null;
    }
}