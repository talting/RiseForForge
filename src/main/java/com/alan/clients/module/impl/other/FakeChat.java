package com.alan.clients.module.impl.other;

import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.DescriptionSetting;
import com.alan.clients.utility.Utils;
import net.minecraft.util.ChatComponentText;

public class FakeChat extends Module {
    public static DescriptionSetting a;
    public static String msg = "&eThis is a fake chat message.";
    public static final String command = "fakechat";
    public static final String c4 = "&cInvalid message.";

    public FakeChat() {
        super("Fake Chat", Module.category.other, 0);
        this.registerSetting(a = new DescriptionSetting(Utils.uf("command") + ": " + command + " [msg]"));
    }

    public void onEnable() {
        if (msg.contains("\\n")) {
            String[] split = msg.split("\\\\n");

            for (String s : split) {
                this.sm(s);
            }
        } else {
            this.sm(msg);
        }

        this.disable();
    }

    private void sm(String txt) {
        mc.thePlayer.addChatMessage(new ChatComponentText(Utils.formatColor(txt)));
    }
}
