package net.redmelon.fishandshiz.entity.custom;

import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.redmelon.fishandshiz.client.PassiveWaterEntity;
import net.redmelon.fishandshiz.client.SchoolingBreedEntity;
import net.redmelon.fishandshiz.client.goals.BreedFollowGroupLeaderGoal;
import net.redmelon.fishandshiz.client.util.ModUtil;
import net.redmelon.fishandshiz.entity.custom.util.AngelfishColor;
import net.redmelon.fishandshiz.entity.custom.util.AngelfishPattern;
import net.redmelon.fishandshiz.item.ModItems;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class AngelfishEntity extends SchoolingBreedEntity implements IAnimatable {

    private static final TrackedData<AngelfishPattern> PATTERN = DataTracker.registerData(AngelfishEntity.class, AngelfishPattern.TRACKED_DATA_HANDLER);
    private static final TrackedData<AngelfishColor> BASE_COLOR = DataTracker.registerData(AngelfishEntity.class, AngelfishColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<AngelfishColor> PATTERN_COLOR = DataTracker.registerData(AngelfishEntity.class, AngelfishColor.TRACKED_DATA_HANDLER);
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(AngelfishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);;

    public AngelfishEntity(EntityType<? extends SchoolingBreedEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return FishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 8.0f, 1.6, 1.4, EntityPredicates.EXCEPT_SPECTATOR::test));
        this.goalSelector.add(4, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(4, new BreedFollowGroupLeaderGoal(this));
    }

    @Override
    protected SoundEvent getFlopSound() {
        return null;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        setPattern(ModUtil.getRandomTagValue(getWorld(), AngelfishPattern.Tag.NATURAL_PATTERNS, random));
        setBaseColor(ModUtil.getRandomTagValue(getWorld(), AngelfishColor.Tag.BASE_COLORS, random));
        setPatternColor(ModUtil.getRandomTagValue(getWorld(), AngelfishColor.Tag.PATTERN_COLORS, random));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(PATTERN, AngelfishPattern.NONE);
        dataTracker.startTracking(BASE_COLOR, AngelfishColor.SILVER);
        dataTracker.startTracking(PATTERN_COLOR, AngelfishColor.SILVER);
        dataTracker.startTracking(FROM_BUCKET, false);
    }

    public void setBaseColor(AngelfishColor color) {
        dataTracker.set(BASE_COLOR, color);
    }

    public AngelfishColor getBaseColor() {
        return dataTracker.get(BASE_COLOR);
    }

    public void setPatternColor(AngelfishColor color) {
        dataTracker.set(PATTERN_COLOR, color);
    }

    public AngelfishColor getPatternColor() {
        return dataTracker.get(PATTERN_COLOR);
    }

    public void setPattern(AngelfishPattern pattern) {
        dataTracker.set(PATTERN, pattern);
    }

    public AngelfishPattern getPattern() {
        return dataTracker.get(PATTERN);
    }

    @Override
    public @Nullable PassiveWaterEntity createChild(ServerWorld var1, PassiveWaterEntity var2) {
        return null;
    }

    @Override
    public void writeCustomDatatoNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("FromBucket", isFromBucket());
        nbt.putInt("LoveTicks", getLoveTicks());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setPattern(AngelfishPattern.fromId(nbt.getString("Pattern")));
        setBaseColor(AngelfishColor.fromId(nbt.getString("BaseColor")));
        setPatternColor(AngelfishColor.fromId(nbt.getString("PatternColor")));
        setFromBucket(nbt.getBoolean("FromBucket"));
        setLoveTicks(nbt.getInt("LoveTicks"));
    }

    private PlayState controller(AnimationEvent<AngelfishEntity> event) {
        if (this.isTouchingWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.angelfish.swim", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.angelfish.flop", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::controller));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public boolean isFromBucket() {
        return dataTracker.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        dataTracker.set(FROM_BUCKET, fromBucket);
    }

    @Override @SuppressWarnings("deprecation")
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
        NbtCompound nbt = stack.getOrCreateNbt();
    }

    @Override @SuppressWarnings("deprecation")
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        if(nbt.contains("Pattern", NbtElement.STRING_TYPE)) {
            readCustomDataFromNbt(nbt);
        }
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.ANGELFISH_BUCKET);
    }
}