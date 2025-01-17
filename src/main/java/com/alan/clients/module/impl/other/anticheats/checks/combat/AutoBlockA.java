package com.alan.clients.module.impl.other.anticheats.checks.combat;

import com.alan.clients.module.impl.other.Anticheat;
import com.alan.clients.module.impl.other.anticheats.Check;
import com.alan.clients.module.impl.other.anticheats.TRPlayer;
import com.alan.clients.module.impl.other.anticheats.config.AdvancedConfig;
import org.jetbrains.annotations.NotNull;

public class AutoBlockA extends Check {
    public AutoBlockA(@NotNull TRPlayer player) {
        super("AutoBlockA", player);
    }

    @Override
    public void _onTick() {
        if (player.fabricPlayer.isBlocking() && !player.lastSwing && player.currentSwing) {
            flag("impossible hit.");
        }
    }

    @Override
    public int getAlertBuffer() {
        return AdvancedConfig.autoBlockAAlertBuffer;
    }

    @Override
    public boolean isDisabled() {
        return !Anticheat.getCombatCheck().isToggled();
    }
}
