package com.alan.clients.module.impl.other.anticheats.checks.movement;

import com.alan.clients.module.impl.other.Anticheat;
import com.alan.clients.module.impl.other.anticheats.Check;
import com.alan.clients.module.impl.other.anticheats.TRPlayer;
import com.alan.clients.module.impl.other.anticheats.config.AdvancedConfig;
import com.alan.clients.utility.Utils;
import org.jetbrains.annotations.NotNull;

public class NoFallA extends Check {
    public NoFallA(@NotNull TRPlayer player) {
        super("NoFallA", player);
    }

    @Override
    public void _onTick() {
        if (!player.fabricPlayer.capabilities.isFlying) {
            double serverPosX = (double) player.fabricPlayer.serverPosX / 32;
            double serverPosY = (double) player.fabricPlayer.serverPosY / 32;
            double serverPosZ= (double) player.fabricPlayer.serverPosZ / 32;
            double deltaX = Math.abs(player.compatPlayerData.serverPosX - serverPosX);
            double deltaY = player.compatPlayerData.serverPosY - serverPosY;
            double deltaZ = Math.abs(player.compatPlayerData.serverPosZ - serverPosZ);
            if (deltaY >= 5 && deltaX <= 10 && deltaZ <= 10 && deltaY <= 40) {
                if (!Utils.overVoid(serverPosX, serverPosY, serverPosZ) && Utils.getFallDistance(player.fabricPlayer) > 3) {
                    flag();
                }
            }
        }
    }

    @Override
    public int getAlertBuffer() {
        return AdvancedConfig.noFallAAlertBuffer;
    }

    @Override
    public boolean isDisabled() {
        return !Anticheat.getMovementCheck().isToggled();
    }
}
