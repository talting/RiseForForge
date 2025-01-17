package com.alan.clients.module.impl.movement.speed;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.impl.client.Notifications;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.MoveUtil;
import com.alan.clients.utility.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class VanillaSpeed extends SubMode<Speed> {
    private final SliderSetting horizonSpeed;
    private final ButtonSetting autoJump;
    private final SliderSetting verticalSpeed;
    private final ButtonSetting fastStop;

    private static boolean warning = false;

    public VanillaSpeed(String name, @NotNull Speed parent) {
        super(name, parent);
        this.registerSetting(horizonSpeed = new SliderSetting("Horizon speed", 1, 0.1, 5, 0.1));
        this.registerSetting(autoJump = new ButtonSetting("Auto jump", true));
        this.registerSetting(verticalSpeed = new SliderSetting("Vertical speed", 1, 0.1, 3, 0.1, autoJump::isToggled));
        this.registerSetting(fastStop = new ButtonSetting("Fast stop", false));
    }

    @Override
    public void onEnable() {
        if (Utils.isHypixel() && !warning) {
            warning = true;
            Notifications.sendNotification(Notifications.NotificationTypes.WARN, "Vanilla Speed on Hypixel may result a ban!");
            parent.disable();
        }
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (parent.noAction()) return;

        if (MoveUtil.isMoving()) {
            if (autoJump.isToggled() && mc.thePlayer.onGround) {
                mc.thePlayer.jump();
                mc.thePlayer.motionY = verticalSpeed.getInput() * MoveUtil.jumpMotion();
                MoveUtil.strafe();
            } else {
                MoveUtil.strafe(horizonSpeed.getInput() * MoveUtil.getAllowedHorizontalDistance());
            }
        } else {
            if (fastStop.isToggled())
                MoveUtil.stop();
        }
    }
}
