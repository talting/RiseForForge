package com.alan.clients.module.setting.impl;

import com.alan.clients.module.Module;
import org.jetbrains.annotations.NotNull;

public class LiteralSubMode extends SubMode<Module> {
    public LiteralSubMode(String name, @NotNull Module parent) {
        super(name, parent);
    }
}
