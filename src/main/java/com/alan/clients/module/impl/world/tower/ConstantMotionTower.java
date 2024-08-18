package com.alan.clients.module.impl.world.tower;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.impl.world.Tower;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.MoveUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class ConstantMotionTower extends SubMode<Tower> {
    private final SliderSetting motion;
    private final ButtonSetting strafe;

    public ConstantMotionTower(String name, @NotNull Tower parent) {
        super(name, parent);
        this.registerSetting(motion = new SliderSetting("Motion", 0.42, 0.1, 1, 0.01));
        this.registerSetting(strafe = new ButtonSetting("Strafe", false));
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (parent.canTower()) {
            mc.thePlayer.motionY = motion.getInput();
            if (strafe.isToggled())
                MoveUtil.strafe();
        }
    }
}
