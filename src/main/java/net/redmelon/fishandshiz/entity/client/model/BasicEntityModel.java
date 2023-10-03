package net.redmelon.fishandshiz.entity.client.model;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static net.redmelon.fishandshiz.FishAndBizzV.MOD_ID;

public class BasicEntityModel<A extends LivingEntity & IAnimatable> extends AnimatedGeoModel<A> {

    private final Identifier model;
    private final Identifier texture;
    private final Identifier animation;
    private final boolean liesOutOfWater;
    private final @Nullable String head;

    public BasicEntityModel(Identifier model, Identifier texture, Identifier animation, boolean liesOutOfWater, @Nullable String head) {
        this.model = model;
        this.texture = texture;
        this.animation = animation;
        this.liesOutOfWater = liesOutOfWater;
        this.head = head;
    }

    public BasicEntityModel(String name, boolean liesOutOfWater, @Nullable String head) {
        this(
                new Identifier(MOD_ID,"geo/" + name + ".geo.json"),
                new Identifier(MOD_ID, "textures/entity/" + name + ".png"),
                new Identifier(MOD_ID, "animations/" + name + ".animation.json"), liesOutOfWater, head);

    }

    public BasicEntityModel(String name, boolean liesOutOfWater) {
        this(name, liesOutOfWater, null);
    }

    @Override
    public Identifier getModelResource(A entity) {
        return model;
    }

    @Override
    public Identifier getTextureResource(A entity) {
        return texture;
    }

    @Override
    public Identifier getAnimationResource(A entity) {
        return animation;
    }
}
