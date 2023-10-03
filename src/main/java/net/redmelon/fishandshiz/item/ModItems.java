package net.redmelon.fishandshiz.item;

import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.redmelon.fishandshiz.FishAndBizzV;
import net.redmelon.fishandshiz.entity.ModEntities;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.redmelon.fishandshiz.FishAndBizzV.MOD_ID;

public class ModItems {
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final Item ANGELFISH_BUCKET = createBucket("angelfish", ModEntities.ANGELFISH);

    private static <T extends Item> T create(String name, T item) {
        ITEMS.put(item, new Identifier(MOD_ID, name));
        return item;
    }

    private static Item createBucket(String name, EntityType<?> type) {
        return create(name + "_bucket", new EntityBucketItem(type, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().group(ItemGroup.MISC).maxCount(1)));
    }
}
