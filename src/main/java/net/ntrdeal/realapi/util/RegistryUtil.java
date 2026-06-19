package net.ntrdeal.realapi.util;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class RegistryUtil {
    public static class ItemUtil {
        public static Function<Item.Properties, Item> blockCustomName(Block block) {
            return Items.createBlockItemWithCustomItemName(block);
        }

        public static Item registerSpawnEgg(ResourceKey<Item> key, EntityType<?> type) {
            return Items.registerSpawnEgg(key, type);
        }

        public static Item registerBlock(BlockItemId id, Block block) {
            return Items.registerBlock(id, block);
        }

        public static Item registerBlock(BlockItemId id, Block block, Item.Properties properties) {
            return Items.registerBlock(id, block, properties);
        }

        public static Item registerBlock(BlockItemId id, Block block, UnaryOperator<Item.Properties> propertiesFunction) {
            return Items.registerBlock(id, block, propertiesFunction);
        }

        public static Item registerBlock(BlockItemId id, Block block, Block... alternatives) {
            return Items.registerBlock(id, block, alternatives);
        }

        public static Item registerBlock(BlockItemId id, Block block, BiFunction<Block, Item.Properties, Item> function) {
            return Items.registerBlock(id, block, function);
        }

        public static Item registerBlock(BlockItemId id, Block block, BiFunction<Block, Item.Properties, Item> function, Item.Properties properties) {
            return Items.registerBlock(id, block, function, properties);
        }

        public static Item registerItem(ResourceKey<Item> key, Item.Properties properties) {
            return Items.registerItem(key, properties);
        }

        public static Item registerItem(ResourceKey<Item> key) {
            return Items.registerItem(key);
        }

        public static Item registerItem(BlockItemId id, Function<Item.Properties, Item> function) {
            return Items.registerItem(id, function);
        }

        public static Item registerItem(ResourceKey<Item> key, Function<Item.Properties, Item> function) {
            return Items.registerItem(key, function);
        }

        public static Item registerItem(BlockItemId id, Function<Item.Properties, Item> function, Item.Properties properties) {
            return Items.registerItem(id, function, properties);
        }

        public static Item registerItem(ResourceKey<Item> key, Function<Item.Properties, Item> function, Item.Properties properties) {
            return Items.registerItem(key, function, properties);
        }
    }

    public static class ComponentUtil {
        public static <T> DataComponentType<T> register(ResourceKey<DataComponentType<?>> key, UnaryOperator<DataComponentType.Builder<T>> operator) {
            return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, key, operator.apply(DataComponentType.builder()).build());
        }
    }

    public static class TabUtil {
        public static CreativeModeTab register(ResourceKey<CreativeModeTab> key, UnaryOperator<CreativeModeTab.Builder> operator) {
            return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, operator.apply(FabricCreativeModeTab.builder()).build());
        }
    }

    public record ResourceCreator<T>(ResourceKey<Registry<T>> registry, Function<String, Identifier> function) {
        public ResourceKey<T> create(String path) {
            return ResourceKey.create(this.registry, this.function.apply(path));
        }
    }

    public static <T> ResourceCreator<T> creator(ResourceKey<Registry<T>> registry, String namespace) {
        return new ResourceCreator<>(registry, path -> Identifier.fromNamespaceAndPath(namespace, path));
    }

    public static <T> ResourceCreator<T> creator(ResourceKey<Registry<T>> registry, Function<String, Identifier> function) {
        return new ResourceCreator<>(registry, function);
    }
}
