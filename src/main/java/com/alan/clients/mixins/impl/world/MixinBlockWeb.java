package com.alan.clients.mixins.impl.world;


import com.alan.clients.Rise;
import com.alan.clients.event.BlockWebEvent;
import com.alan.clients.utility.Utils;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockWeb.class)
public class MixinBlockWeb {

    @Inject(method = "onEntityCollidedWithBlock", at = @At("HEAD"), cancellable = true)
    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState state, Entity entity, CallbackInfo ci) {
        if (Utils.nullCheck() && entity == Rise.mc.thePlayer) {
            BlockWebEvent event = new BlockWebEvent(blockPos, state);
            MinecraftForge.EVENT_BUS.post(event);

            if (event.isCanceled())
                ci.cancel();
        }
    }
}
