package com.alan.clients.module.impl.other.anticheats.checks.movement;

import com.alan.clients.module.impl.other.Anticheat;
import com.alan.clients.module.impl.other.anticheats.Check;
import com.alan.clients.module.impl.other.anticheats.TRPlayer;
import com.alan.clients.module.impl.other.anticheats.config.AdvancedConfig;
import org.jetbrains.annotations.NotNull;


public class BlinkA extends Check {
    
    public BlinkA(@NotNull TRPlayer player) {
        super("BlinkA", player);
    }

    @Override
    public void _onTick() {
        if (player.lastPos == null || player.hasSetback) return;

        if (player.lastPos.distanceTo(player.currentPos) > (
                AdvancedConfig.blinkMaxDistance * player.speedMul + player.fabricPlayer.fallDistance + Anticheat.getThreshold().getInput())) {
            flag();
        }
    }

    @Override
    public int getAlertBuffer() {
        return AdvancedConfig.blinkAlertBuffer;
    }

    @Override
    public boolean isDisabled() {
        return !Anticheat.getMovementCheck().isToggled();
    }
}
