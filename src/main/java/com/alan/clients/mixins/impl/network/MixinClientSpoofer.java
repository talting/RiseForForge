package com.alan.clients.mixins.impl.network;

import com.alan.clients.module.impl.exploit.ClientSpoofer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FMLCommonHandler.class)
public abstract class MixinClientSpoofer {
        @Inject(method = "getModName", at = @At("RETURN"), cancellable = true, remap = false)
        private void getModName(@NotNull CallbackInfoReturnable<String> cir) {
            cir.setReturnValue(ClientSpoofer.getBrandName().brand);
    }
}