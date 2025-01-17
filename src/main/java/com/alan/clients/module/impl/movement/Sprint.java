package com.alan.clients.module.impl.movement;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.ModuleManager;
import com.alan.clients.module.setting.impl.ModeSetting;
import com.alan.clients.module.setting.utils.ModeOnly;
import com.alan.clients.utility.MoveUtil;
import com.alan.clients.utility.Utils;
import com.alan.clients.utility.movement.Move;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class Sprint extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", new String[]{"Legit", "Omni"}, 0);
    private final ModeSetting omniMode = new ModeSetting("Bypass mode", new String[]{"None", "Hypixel", "Legit"}, 1, new ModeOnly(mode, 1));
    public static boolean omni = false;
    public static boolean stopSprint = false;

    public Sprint() {
        super("Sprint", Module.category.movement, 0);
        this.registerSetting(mode, omniMode);
    }

    public static boolean omni() {
        return omni || ModuleManager.sprint != null && ModuleManager.sprint.isEnabled() && ModuleManager.sprint.mode.getInput() == 1 && MoveUtil.isMoving();
    }

    public static boolean stopSprint() {
        return stopSprint;
    }

    @SubscribeEvent
    public void p(PlayerTickEvent e) {
        if (Utils.nullCheck() && mc.inGameHasFocus) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
        }
    }

    @SubscribeEvent
    public void onPreMotion(PreMotionEvent event) {
        if (mode.getInput() != 1) return;

        switch ((int) omniMode.getInput()) {
            case 0:
                break;
            case 1:
                if (mc.thePlayer.moveForward <= 0)
                    event.setSprinting(false);
                break;
            case 2:
                event.setYaw(event.getYaw() + Move.fromMovement(mc.thePlayer.moveForward, mc.thePlayer.moveStrafing).getDeltaYaw());
                break;
        }

        if (MoveUtil.isMoving())
            mc.thePlayer.setSprinting(true);
    }
}
