package com.alan.clients.module.impl.combat.criticals;

import com.alan.clients.event.PreMotionEvent;
import com.alan.clients.module.impl.combat.Criticals;
import com.alan.clients.module.setting.impl.SubMode;
import com.alan.clients.utility.Utils;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class NoGroundCriticals extends SubMode<Criticals> {
    public NoGroundCriticals(String name, @NotNull Criticals parent) {
        super(name, parent);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onPreMotion(PreMotionEvent event) {
        if (Utils.isTargetNearby()) {
            event.setOnGround(false);
        }
    }
}
