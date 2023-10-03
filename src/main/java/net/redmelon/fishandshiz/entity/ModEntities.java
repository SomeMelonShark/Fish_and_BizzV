package net.redmelon.fishandshiz.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.redmelon.fishandshiz.entity.custom.AngelfishEntity;

import static net.redmelon.fishandshiz.FishAndBizzV.MOD_ID;

public class ModEntities {

    public static final EntityType<AngelfishEntity> ANGELFISH = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(MOD_ID, "angelfish"),
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(AngelfishEntity::new)
                    .defaultAttributes(FishEntity::createFishAttributes)
                    .dimensions(EntityDimensions.fixed(0.4f, 0.6f))
                    .spawnGroup(SpawnGroup.WATER_AMBIENT)
                    .spawnRestriction(SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterCreatureEntity::canSpawn)
                    .build()
    );
}
