package com.alan.clients.module.impl.player;

import com.alan.clients.module.Module;
import com.alan.clients.module.impl.player.nofall.*;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.ModeValue;
import com.alan.clients.utility.*;

public class NoFall extends Module {
    private final ModeValue mode;
    private final ButtonSetting ignoreVoid;

    public NoFall() {
        super("NoFall", category.player);
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new HypixelBlinkNoFall("HypixelBlink", this))
                .add(new HypixelPacketNoFall("HypixelPacket", this))
                .add(new LegitNoFall("Legit", this))
                .add(new NoGroundNoFall("NoGround", this))
                .add(new OnGround1NoFall("OnGround1", this))
                .add(new OnGround2NoFall("OnGround2", this))
                .add(new VulcanNoFall("Vulcan", this))
        );
        this.registerSetting(ignoreVoid = new ButtonSetting("Ignore void", true));
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
        return mode.getSubModeValues().get((int) mode.getInput()).getPrettyName();
    }

    public boolean noAction() {
        return ignoreVoid.isToggled() && Utils.overVoid();
    }
}
