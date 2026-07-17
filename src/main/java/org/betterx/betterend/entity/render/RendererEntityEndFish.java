package org.betterx.betterend.entity.render;

import org.betterx.betterend.BetterEnd;
import org.betterx.betterend.entity.EndFishEntity;
import org.betterx.betterend.entity.model.EndFishEntityModel;
import org.betterx.betterend.registry.EndEntitiesRenders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RendererEntityEndFish extends MobRenderer<EndFishEntity, EndFishEntityModel> {
    private static final ResourceLocation[] TEXTURE = new ResourceLocation[EndFishEntity.VARIANTS];
    private static final RenderType[] GLOW = new RenderType[EndFishEntity.VARIANTS];

    public RendererEntityEndFish(EntityRendererProvider.Context ctx) {
        super(ctx, new EndFishEntityModel(ctx.bakeLayer(EndEntitiesRenders.END_FISH_MODEL)), 0.5f);
        this.addLayer(new EyesLayer<EndFishEntity, EndFishEntityModel>(this) {
            @Override
            public RenderType renderType() {
                return GLOW[0];
            }

            @Override
            public void render(
                    PoseStack matrices,
                    MultiBufferSource vertexConsumers,
                    int light,
                    EndFishEntity entity,
                    float limbAngle,
                    float limbDistance,
                    float tickDelta,
                    float animationProgress,
                    float headYaw,
                    float headPitch
            ) {
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(GLOW[entity.getVariant()]);
                this.getParentModel()
                    .renderToBuffer(
                            matrices,
                            vertexConsumer,
                            15728640,
                            OverlayTexture.NO_OVERLAY,
                            0xffffffff
                    );
            }
        });
    }

    @Override
    protected void scale(EndFishEntity entity, PoseStack matrixStack, float f) {
        float scale = entity.getScale();
        matrixStack.scale(scale, scale, scale);
    }

    @Override
    public ResourceLocation getTextureLocation(EndFishEntity entity) {
        return TEXTURE[entity.getVariant()];
    }

    static {
        for (int i = 0; i < EndFishEntity.VARIANTS; i++) {
            TEXTURE[i] = BetterEnd.C.mk("textures/entity/end_fish/end_fish_" + i + ".png");
            GLOW[i] = RenderType.eyes(BetterEnd.C.mk("textures/entity/end_fish/end_fish_" + i + "_glow.png"));
        }
    }
}
