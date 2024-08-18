package com.alan.clients.mixins.impl.network;


import com.alan.clients.Rise;
import com.alan.clients.event.PostVelocityEvent;
import com.alan.clients.event.PreVelocityEvent;
import com.alan.clients.module.ModuleManager;
import com.alan.clients.utility.Utils;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient {

    @Inject(method = "handleEntityVelocity", at = @At("HEAD"), cancellable = true)
    public void onPreHandleEntityVelocity(S12PacketEntityVelocity packet, CallbackInfo ci) {
        if (!Utils.nullCheck()) return;

        if (packet.getEntityID() == Rise.mc.thePlayer.getEntityId()) {
            if (ModuleManager.fly.isEnabled() || ModuleManager.longJump.isEnabled()) return;

            PreVelocityEvent event = new PreVelocityEvent(packet.getMotionX(), packet.getMotionY(), packet.getMotionZ());
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled()) ci.cancel();

            S12PacketEntityVelocityAccessor accessor = (S12PacketEntityVelocityAccessor) packet;
            accessor.setMotionX(event.getMotionX());
            accessor.setMotionY(event.getMotionY());
            accessor.setMotionZ(event.getMotionZ());
        }
    }

    @Inject(method = "handleEntityVelocity", at = @At("RETURN"))
    public void onPostHandleEntityVelocity(S12PacketEntityVelocity packet, CallbackInfo ci) {
        if (!Utils.nullCheck()) return;

        if (packet.getEntityID() == Rise.mc.thePlayer.getEntityId()) {
            MinecraftForge.EVENT_BUS.post(new PostVelocityEvent());
        }
    }
}
