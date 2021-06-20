package me.jaackson.mannequins.bridge.fabric;

import me.jaackson.mannequins.Mannequins;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Jackson
 */
public class RegistryBridgeImpl {

    public static <T extends SoundEvent> Supplier<T> registerSound(String name, Supplier<T> object) {
        T register = Registry.register(Registry.SOUND_EVENT, new ResourceLocation(Mannequins.MOD_ID, name), object.get());
        return () -> register;
    }

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> object) {
        T register = Registry.register(Registry.ITEM, new ResourceLocation(Mannequins.MOD_ID, name), object.get());
        return () -> register;
    }

    public static <E extends Entity, T extends EntityType.Builder<E>> Supplier<EntityType<E>> registerEntity(String name, Supplier<T> object) {
        EntityType<E> register = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(Mannequins.MOD_ID, name), object.get().build(Mannequins.MOD_ID + ":" + name));
        return () -> register;
    }

    public static <T extends LivingEntity> void registerEntityAttributes(Supplier<EntityType<T>> type, Supplier<AttributeSupplier.Builder> builder) {
        FabricDefaultAttributeRegistry.register(type.get(), builder.get());
    }

    @Environment(EnvType.CLIENT)
    public static <T extends Entity> void registerEntityRenderer(EntityType<T> entity, Function<EntityRenderDispatcher, EntityRenderer<T>> renderer) {
        EntityRendererRegistry.INSTANCE.register(entity, (manager, context) -> renderer.apply(manager));
    }

    @Environment(EnvType.CLIENT)
    public static void registerSprite(ResourceLocation sprite, ResourceLocation atlas) {
        ClientSpriteRegistryCallback.event(atlas).register((atlasTexture, registry) -> registry.register(sprite));
    }

}
