package org.betterx.betterend.mixin.client;

import org.betterx.betterend.client.render.ArmoredElytraLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidMobRenderer.class)
public abstract class HumanoidMobRendererMixin {

    @Inject(
            method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Lnet/minecraft/client/model/HumanoidModel;Lnet/minecraft/client/model/HumanoidModel;FLnet/minecraft/client/renderer/entity/layers/CustomHeadLayer$Transforms;)V",
            at = @At("TAIL")
    )
    public void be_addCustomLayer(
            EntityRendererProvider.Context context,
            Object adultModel,
            Object babyModel,
            float f,
            CustomHeadLayer.Transforms transforms,
            CallbackInfo ci
    ) {
        addArmoredElytraLayer(context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void addArmoredElytraLayer(EntityRendererProvider.Context context) {
        LivingEntityRenderer renderer = (LivingEntityRenderer) (Object) this;
        RenderLayerParent parent = (RenderLayerParent) (Object) this;
        renderer.addLayer(new ArmoredElytraLayer(parent, context.getModelSet()));
    }
}
