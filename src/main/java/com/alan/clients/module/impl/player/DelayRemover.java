package com.alan.clients.module.impl.player;

import com.alan.clients.module.Module;
import com.alan.clients.module.ModuleManager;
import com.alan.clients.module.setting.impl.ButtonSetting;
import com.alan.clients.utility.Reflection;
import com.alan.clients.utility.Utils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;

public class DelayRemover extends Module { // from b4 src
    private final ButtonSetting oldReg;
    private final ButtonSetting removeJumpTicks;
    private final ButtonSetting notWhileScaffold;

    public DelayRemover() {
        super("Delay Remover", category.player, 0);
        this.registerSetting(oldReg = new ButtonSetting("1.7 hitReg", true));
        this.registerSetting(removeJumpTicks = new ButtonSetting("Remove jump ticks", false));
        this.registerSetting(notWhileScaffold = new ButtonSetting("Not while scaffold", false, removeJumpTicks::isToggled));
    }

    @SubscribeEvent
    public void onTick(final TickEvent.@NotNull PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !mc.inGameHasFocus || !Utils.nullCheck()) {
            return;
        }
        if (oldReg.isToggled()) {
            try {
                Reflection.leftClickCounter.set(mc, 0);
            } catch (IllegalAccessException | IndexOutOfBoundsException ignored) {
            }
        }
        if (removeJumpTicks.isToggled() && (!notWhileScaffold.isToggled() || !ModuleManager.scaffold.isEnabled())) {
            try {
                Reflection.jumpTicks.set(mc.thePlayer, 0);
            } catch (IllegalAccessException | IndexOutOfBoundsException ignored) {
            }
        }
    }
}
