package com.alan.clients.module.impl.movement.speed.hypixel;

import com.alan.clients.event.PrePlayerInputEvent;
import com.alan.clients.module.impl.movement.speed.HypixelSpeed;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.MoveUtil;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Vulcan speed lol
 */
public class HypixelLowHopSpeed extends SubMode<HypixelSpeed> {
    private final ButtonSetting strafe;

    private float angle = 0;

    public HypixelLowHopSpeed(String name, @NotNull HypixelSpeed parent) {
        super(name, parent);
        this.registerSetting(strafe = new ButtonSetting("Strafe", false));
    }

    @Override
    public void onEnable() {
        angle = (float) Math.toDegrees(MoveUtil.direction());
    }

    @SubscribeEvent
    public void onPrePlayerInput(PrePlayerInputEvent event) {
        if (!MoveUtil.isMoving()) return;
        switch (parent.parent.offGroundTicks) {
            case 0:
                mc.thePlayer.jump();

                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    MoveUtil.strafe(0.6);
                } else {
                    MoveUtil.strafe(0.485);
                }
                break;
            case 5:
                mc.thePlayer.motionY = MoveUtil.predictedMotion(mc.thePlayer.motionY, 2);
                angle = (float) Math.toDegrees(MoveUtil.direction());
                break;
            default:
                if (strafe.isToggled() && parent.parent.offGroundTicks > 5)
                    angle = MoveUtil.simulationStrafeAngle(angle, 10.0F);
                break;
        }
    }

}
