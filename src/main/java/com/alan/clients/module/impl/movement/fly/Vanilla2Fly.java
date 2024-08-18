package com.alan.clients.module.impl.movement.fly;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.impl.movement.Fly;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.MoveUtil;
import com.alan.clients.utility.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class Vanilla2Fly extends SubMode<Fly> {
    private final SliderSetting horizontalSpeed;
    private final SliderSetting verticalSpeed;
    private final ButtonSetting groundSpoof;

    public Vanilla2Fly(String name, @NotNull Fly parent) {
        super(name, parent);
        this.registerSetting(horizontalSpeed = new SliderSetting("Horizontal speed", 2.0, 0.0, 9.0, 0.1));
        this.registerSetting(verticalSpeed = new SliderSetting("Vertical speed", 2.0, 0.0, 9.0, 0.1));
        this.registerSetting(groundSpoof = new ButtonSetting("Ground spoof", false));
    }

    @Override
    public void onUpdate() {
        if (mc.currentScreen == null) {
            if (Utils.jumpDown()) {
                mc.thePlayer.motionY = 0.3 * verticalSpeed.getInput();
            } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.thePlayer.motionY = -0.3 * verticalSpeed.getInput();
            } else {
                mc.thePlayer.motionY = 0.0;
            }
        }
        else {
            mc.thePlayer.motionY = 0.0;
        }
        if (MoveUtil.isMoving())
            MoveUtil.strafe(0.85 * horizontalSpeed.getInput());
        else
            MoveUtil.stop();
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (groundSpoof.isToggled()) {
            event.setOnGround(true);
        }
    }
}
