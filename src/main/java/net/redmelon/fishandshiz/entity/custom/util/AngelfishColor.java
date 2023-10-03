package net.redmelon.fishandshiz.entity.custom.util;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.redmelon.fishandshiz.FishAndBizzV.MOD_ID;

public record AngelfishColor(int color) {

    private static final Map<AngelfishColor, Identifier> COLORS = new LinkedHashMap<>();

    public static final AngelfishColor SILVER = create("ivory", 0xb6b6b6);
    public static final AngelfishColor MELANATED = create("melanated", 0x241e1e);
    public static final AngelfishColor COCOA = create("cocoa", 0x35342f);
    public static final AngelfishColor CREAM = create("cream", 0xab9982);
    public static final AngelfishColor NEON_BLUE = create("neon_blue", 0x084ab1);
    public static final AngelfishColor GOLD = create("gold", 0xc58921);

    public static final Registry<AngelfishColor> REGISTRY = FabricRegistryBuilder
            .createDefaulted(AngelfishColor.class, new Identifier(MOD_ID, "angelfish_color"), new Identifier(MOD_ID, "silver"))
            .attribute(RegistryAttribute.SYNCED).buildAndRegister();

    public static final TrackedDataHandler<AngelfishColor> TRACKED_DATA_HANDLER = TrackedDataHandler.of(REGISTRY);

    public String getTranslationKey() {
        return "sea_slug_color." + this.getId().getNamespace() + "." + this.getId().getPath();
    }

    public Identifier getId() {
        return REGISTRY.getId(this);
    }

    public static AngelfishColor fromId(String id) {
        return fromId(Identifier.tryParse(id));
    }

    public static AngelfishColor fromId(Identifier id) {
        return REGISTRY.get(id);
    }

    private static AngelfishColor create(String name, int color) {
        AngelfishColor angelfishColor = new AngelfishColor(color);
        COLORS.put(angelfishColor, new Identifier(MOD_ID, name));
        return angelfishColor;
    }

    public static void init() {
        TrackedDataHandlerRegistry.register(TRACKED_DATA_HANDLER);
        COLORS.keySet().forEach(color -> Registry.register(REGISTRY, COLORS.get(color), color));
    }

    public static class Tag {

        public static final TagKey<AngelfishColor> BASE_COLORS = of("base_colors");
        public static final TagKey<AngelfishColor> PATTERN_COLORS = of("pattern_colors");

        private static TagKey<AngelfishColor> of(String id) {
            return of(new Identifier(MOD_ID, id));
        }

        public static TagKey<AngelfishColor> of(Identifier id) {
            return TagKey.of(REGISTRY.getKey(), id);
        }
    }
}
