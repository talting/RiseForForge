package com.alan.clients.module.impl.other.anticheats.checks.movement;

import com.alan.clients.module.impl.other.Anticheat;
import com.alan.clients.module.impl.other.anticheats.Check;
import com.alan.clients.module.impl.other.anticheats.TRPlayer;
import com.alan.clients.module.impl.other.anticheats.config.AdvancedConfig;
import org.jetbrains.annotations.NotNull;

public class SpeedB extends Check {
    public SpeedB(@NotNull TRPlayer player) {
        super("SpeedB", player);
    }

    @Override
    public void _onTick() {
        if (player.fabricPlayer.isSprinting() && player.fabricPlayer.getFoodStats().getFoodLevel() <= 6) {
            flag();
        }
    }

    @Override
    public int getAlertBuffer() {
        return AdvancedConfig.speedBAlertBuffer;
    }

    @Override
    public boolean isDisabled() {
        return !Anticheat.getMovementCheck().isToggled();
    }
}
