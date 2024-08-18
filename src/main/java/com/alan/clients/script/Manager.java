package com.alan.clients.script;

import com.alan.clients.Rise;
import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.utility.Utils;
import org.lwjgl.Sys;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class Manager extends Module {
    private long lastLoad;
    public final String documentationURL = "https://blowsy.gitbook.io/raven";
    public Manager() {
        super("Manager", category.scripts);
        this.registerSetting(new ButtonSetting("Load scripts", () -> {
            if (Rise.scriptManager.compiler == null) {
                Utils.sendMessage("&cCompiler error, JDK not found");
            }
            else {
                final long currentTimeMillis = System.currentTimeMillis();
                if (Utils.getDifference(this.lastLoad, currentTimeMillis) > 1500) {
                    this.lastLoad = currentTimeMillis;
                    Rise.scriptManager.loadScripts();
                    if (Rise.scriptManager.scripts.isEmpty()) {
                        Utils.sendMessage("&7No scripts found.");
                    }
                    else {
                        Utils.sendMessage("&7Loaded &b" + Rise.scriptManager.scripts.size() + " &7script" + ((Rise.scriptManager.scripts.size() == 1) ? "." : "s."));
                    }
                }
                else {
                    Utils.sendMessage("&cYou are on cooldown.");
                }
            }
        }));
        this.registerSetting(new ButtonSetting("Open folder", () -> {
            try {
                Desktop.getDesktop().open(Rise.scriptManager.directory);
            }
            catch (IOException ex) {
                Rise.scriptManager.directory.mkdirs();
                Utils.sendMessage("&cError locating folder, recreated.");
            }
        }));
        this.registerSetting(new ButtonSetting("View documentation", () -> {
            try {
                Desktop.getDesktop().browse(new URI(documentationURL));
            } catch (Throwable t) {
                Sys.openURL(documentationURL);
            }
        }));
        this.canBeEnabled = false;
        this.ignoreOnSave = true;
    }
}
