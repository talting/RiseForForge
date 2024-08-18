package com.alan.clients.module.impl.fun;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.event.RotationEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.impl.fun.antiaim.Backward;
import com.alan.clients.module.impl.fun.antiaim.Spin;
import com.alan.clients.module.impl.other.RotationHandler;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.ModeValue;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class AntiAim extends Module {
    private final ModeValue mode;
    private final ButtonSetting cancelSprint;
    private final ButtonSetting moveFix;

    public AntiAim() {
        super("AntiAim", category.fun);
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new Spin("Spin", this))
                .add(new Backward("Backward", this))
        );
        this.registerSetting(moveFix = new ButtonSetting("Move fix", false));
        this.registerSetting(cancelSprint = new ButtonSetting("Cancel sprint", false));
    }

    @Override
    public void onEnable() {
        mode.enable();
    }

    @Override
    public void onDisable() {
        mode.disable();
    }

    @SubscribeEvent
    public void onRotation(@NotNull RotationEvent event) {
        event.setMoveFix(moveFix.isToggled() ? RotationHandler.MoveFix.Silent : RotationHandler.MoveFix.None);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onPreMotion(PreMotionEvent event) {
        if (cancelSprint.isToggled()) {
            event.setSprinting(false);
        }
    }
}
