package com.alan.clients.module.impl.movement;

import com.alan.clients.module.Module;
import com.alan.clients.module.impl.movement.longjump.HypixelFireballLongJump;
import com.alan.clients.module.impl.movement.longjump.HypixelLongJump;
import com.alan.clients.module.impl.movement.longjump.VulcanLongJump;
import com.alan.clients.module.setting.impl.ModeValue;

public class LongJump extends Module {
    private final ModeValue mode;

    public LongJump() {
        super("LongJump", category.movement);
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new HypixelLongJump("Hypixel", this))
                .add(new HypixelFireballLongJump("HypixelFireball", this))
                .add(new VulcanLongJump("Vulcan", this))
        );
    }

    @Override
    public void onEnable() {
        mode.enable();
    }

    @Override
    public void onDisable() {
        mode.disable();
    }

    @Override
    public String getInfo() {
        return mode.getSelected().getPrettyName();
    }
}
