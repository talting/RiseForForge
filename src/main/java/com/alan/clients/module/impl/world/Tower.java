package com.alan.clients.module.impl.world;

import com.alan.clients.module.Module;
import com.alan.clients.module.ModuleManager;
import com.alan.clients.module.impl.world.tower.*;
import com.alan.clients.module.setting.impl.*;
import com.alan.clients.utility.Utils;
import org.lwjgl.input.Keyboard;

import static com.alan.clients.module.ModuleManager.scaffold;

public class Tower extends Module {
    private final ButtonSetting disableWhileCollided;
    private final ButtonSetting disableWhileHurt;
    private final ButtonSetting sprintJumpForward;

    public Tower() {
        super("Tower", category.world);
        this.registerSetting(new DescriptionSetting("Works with SafeWalk & Scaffold"));
        final ModeValue mode;
        this.registerSetting(mode = new ModeValue("Mode", this)
                .add(new VanillaTower("Vanilla", this))
                .add(new HypixelJumpSprintTower("HypixelJumpSprint", this))
                .add(new HypixelFastTower("HypixelFast", this))
                .add(new HypixelFastVerticalTower("Rise", this))
                .add(new BlocksMCTower("BlocksMC", this))
                .add(new ConstantMotionTower("ConstantMotion", this))
                .add(new VulcanTower("Vulcan", this))
        );

        this.registerSetting(disableWhileCollided = new ButtonSetting("Disable while collided", false));
        this.registerSetting(disableWhileHurt = new ButtonSetting("Disable while hurt", false));
        this.registerSetting(sprintJumpForward = new ButtonSetting("Sprint jump forward", true));
        this.canBeEnabled = false;

        mode.enable();
    }

    public boolean canTower() {
        if (scaffold.totalBlocks() == 0) return false;
        if (mc.currentScreen != null) return false;
        if (!Utils.nullCheck() || !Utils.jumpDown()) {
            return false;
        }
        else if (disableWhileHurt.isToggled() && mc.thePlayer.hurtTime >= 9) {
            return false;
        }
        else if (disableWhileCollided.isToggled() && mc.thePlayer.isCollidedHorizontally) {
            return false;
        }
        else return modulesEnabled();
    }

    public boolean modulesEnabled() {
        return ((ModuleManager.safeWalk.isEnabled() && ModuleManager.safeWalk.tower.isToggled() && SafeWalk.canSafeWalk()) || (scaffold.isEnabled() && scaffold.tower.isToggled()));
    }

    public boolean canSprint() {
        return canTower() && this.sprintJumpForward.isToggled() && Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()) && Utils.jumpDown();
    }
}
