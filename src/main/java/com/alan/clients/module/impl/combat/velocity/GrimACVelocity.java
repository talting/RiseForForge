package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.event.PostVelocityEvent;
import com.alan.clients.event.PreUpdateEvent;
import com.alan.clients.module.ModuleManager;
import com.alan.clients.module.impl.combat.KillAura;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.module.setting.impl.DescriptionSetting;
import com.alan.clients.module.setting.impl.SliderSetting;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.MoveUtil;
import com.alan.clients.utility.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class GrimACVelocity extends SubMode<Velocity> {
    private final SliderSetting reduceCountEveryTime;
    private final SliderSetting reduceTimes;
    private final ButtonSetting onlyWhileMoving;
    private final ButtonSetting debug;

    private int unReduceTimes = 0;

    public GrimACVelocity(String name, @NotNull Velocity parent) {
        super(name, parent);
        this.registerSetting(new DescriptionSetting("Only work on 1.9+"));
        this.registerSetting(reduceCountEveryTime = new SliderSetting("Reduce count every time", 4, 1, 10, 1));
        this.registerSetting(reduceTimes = new SliderSetting("Reduce times", 1, 1, 5, 1));
        this.registerSetting(onlyWhileMoving = new ButtonSetting("Only while moving", false));
        this.registerSetting(debug = new ButtonSetting("Debug", false));
    }

    @SubscribeEvent
    public void onPreUpdate(PreUpdateEvent event) {
        if (unReduceTimes > 0 && mc.thePlayer.hurtTime > 0 && !(onlyWhileMoving.isToggled() && !MoveUtil.isMoving()) && !ModuleManager.killAura.noAimToEntity()) {
            for (int i = 0; i < (int) reduceCountEveryTime.getInput(); i++) {
                Utils.attackEntity(KillAura.target, debug.isToggled());
            }
            if (debug.isToggled())
                Utils.sendMessage(String.format("%d Reduced %.3f %.3f", (int) reduceTimes.getInput() - unReduceTimes,  mc.thePlayer.motionX, mc.thePlayer.motionZ));
            unReduceTimes--;
        } else {
            unReduceTimes = 0;
        }
    }

    @SubscribeEvent
    public void onPostVelocity(PostVelocityEvent event) {
        unReduceTimes = (int) reduceTimes.getInput();
    }
}
