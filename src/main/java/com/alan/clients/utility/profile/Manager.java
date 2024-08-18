package com.alan.clients.utility.profile;

import com.alan.clients.Rise;
import com.alan.clients.module.Module;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.utility.Utils;

import java.awt.*;
import java.io.IOException;

public class Manager extends Module {
    private ButtonSetting loadProfiles, openFolder, createProfile;

    public Manager() {
        super("Manager", category.profiles);
        this.registerSetting(createProfile = new ButtonSetting("Create profile", () -> {
            if (Utils.nullCheck() && Rise.profileManager != null) {
                String name = "profile-";
                for (int i = 1; i <= 100; i++) {
                    if (Rise.profileManager.getProfile(name + i) != null) {
                        continue;
                    }
                    name += i;
                    Rise.profileManager.saveProfile(new Profile(name, 0));
                    Utils.sendMessage("&7Created profile: &b" + name);
                    Rise.profileManager.loadProfiles();
                    break;
                }
            }
        }));
        this.registerSetting(loadProfiles = new ButtonSetting("Load profiles", () -> {
            if (Utils.nullCheck() && Rise.profileManager != null) {
                Rise.profileManager.loadProfiles();
            }
        }));
        this.registerSetting(openFolder = new ButtonSetting("Open folder", () -> {
            try {
                Desktop.getDesktop().open(Rise.profileManager.directory);
            }
            catch (IOException ex) {
                Rise.profileManager.directory.mkdirs();
                Utils.sendMessage("&cError locating folder, recreated.");
            }
        }));
        ignoreOnSave = true;
        canBeEnabled = false;
    }
}