package com.alan.clients.module.impl.movement.noweb;

import com.alan.clients.event.BlockWebEvent;
import com.alan.clients.module.impl.movement.NoWeb;
import com.alan.clients.module.setting.impl.SubMode;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class IgnoreNoWeb extends SubMode<NoWeb> {
    public IgnoreNoWeb(String name, @NotNull NoWeb parent) {
        super(name, parent);
    }

    @SubscribeEvent
    public void onBlockWeb(@NotNull BlockWebEvent event) {
        event.setCanceled(true);
    }
}
