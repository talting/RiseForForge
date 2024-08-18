package com.alan.clients.module.impl.other;

import com.alan.clients.Rise;
import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.ModeSetting;
import com.alan.clients.utility.PacketUtils;
import com.alan.clients.utility.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;

import java.io.*;
import java.util.*;

public class StaffDetector extends Module {
    public static final String[] STAFFLISTS = new String[]{"Hypixel", "BlocksMC", "Gamster", "GommeHD", "Pika", "Syuu", "Stardix", "MinemenClub", "MushMC"};
    public static final List<Set<String>> STAFFS = new ArrayList<>();
    public static final Set<String> hasFlagged = new HashSet<>();

    private final ModeSetting mode = new ModeSetting("Mode", STAFFLISTS, 0);
    private final ButtonSetting autoLobby = new ButtonSetting("Auto lobby", false);
    private final ButtonSetting alarm = new ButtonSetting("Alarm", false);

    public StaffDetector() {
        super("StaffDetector", category.other);
        this.registerSetting(mode, autoLobby, alarm);

        for (String s : STAFFLISTS) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(Rise.class.getResourceAsStream("/assets/rise/stafflists/" + s + ".txt"))))) {
                Set<String> lines = new HashSet<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }

                STAFFS.add(lines);
            } catch (NullPointerException | IOException ignored) {
            }
        }
    }

    @Override
    public void onUpdate() {
        for (EntityPlayer player : mc.theWorld.playerEntities) {
            final String name = player.getName();
            if (hasFlagged.contains(name)) continue;

            if (STAFFS.get((int) mode.getInput()).contains(name)) {
                hasFlagged.add(name);

                Utils.sendMessage("§c§lStaff Detected: §r" + name);
                if (autoLobby.isToggled()) {
                    PacketUtils.sendPacket(new C01PacketChatMessage("/lobby"));
                    Utils.sendMessage("Return to lobby...");
                }
                if (alarm.isToggled()) {
                    mc.thePlayer.playSound("rise:alarm", 1, 1);
                }
            }
        }
    }
}
