package com.alan.clients.module.impl.movement.noslow;

import com.alan.clients.module.impl.movement.NoSlow;
import com.alan.clients.module.setting.impl.SubMode;
import org.jetbrains.annotations.NotNull;

public abstract class INoSlow extends SubMode<NoSlow> {
    public INoSlow(String name, @NotNull NoSlow parent) {
        super(name, parent);
    }

    public abstract float getSlowdown();

    public float getStrafeSlowdown() {
        return getSlowdown();
    }
}
