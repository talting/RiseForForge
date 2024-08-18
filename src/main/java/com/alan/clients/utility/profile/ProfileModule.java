package com.alan.clients.utility.profile;

import com.alan.clients.Rise;
import com.alan.clients.clickgui.ClickGui;
import com.alan.clients.module.Module;
import com.alan.clients.module.impl.client.Settings;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.utility.Utils;

public class ProfileModule extends Module {
    private ButtonSetting saveProfile, removeProfile;
    private Profile profile;
    public boolean saved = true;

    public ProfileModule(Profile profile, String name, int bind) {
        super(name, category.profiles, bind);
        this.profile = profile;
        this.registerSetting(saveProfile = new ButtonSetting("Save profile", () -> {
            Utils.sendMessage("&7Saved profile: &b" + getName());
            Rise.profileManager.saveProfile(this.profile);
            saved = true;
        }));
        this.registerSetting(removeProfile = new ButtonSetting("Remove profile", () -> {
            Utils.sendMessage("&7Removed profile: &b" + getName());
            Rise.profileManager.deleteProfile(getName());
        }));
    }

    @Override
    public void toggle() {
        if (mc.currentScreen instanceof ClickGui || mc.currentScreen == null) {
            if (this.profile == Rise.currentProfile) {
                return;
            }
            Rise.profileManager.loadProfile(this.getName());

            Rise.currentProfile = profile;

            if (Settings.sendMessage.isToggled()) {
                Utils.sendMessage("&7Enabled profile: &b" + this.getName());
            }
            saved = true;
        }
    }

    @Override
    public boolean isEnabled() {
        if (Rise.currentProfile == null) {
            return false;
        }
        return Rise.currentProfile.getModule() == this;
    }
}
