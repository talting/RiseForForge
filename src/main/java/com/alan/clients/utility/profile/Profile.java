package com.alan.clients.utility.profile;

import com.alan.clients.module.Module;

public class Profile {
    private final Module module;
    private final int bind;
    private final String profileName;

    public Profile(String profileName, int bind) {
        this.profileName = profileName;
        this.bind = bind;
        this.module = new ProfileModule(this, profileName, bind);
        this.module.ignoreOnSave = true;
    }

    public Module getModule() {
        return module;
    }

    public int getBind() {
        return bind;
    }

    public String getName() {
        return profileName;
    }
}
