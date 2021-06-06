package me.jaackson.mannequins.bridge.forge;

import me.jaackson.mannequins.Mannequins;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class RegistryBridgeImpl {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Mannequins.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Mannequins.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Mannequins.MOD_ID);

    public static <T extends SoundEvent> Supplier<T> registerSound(String name, T event) {
        return SOUND_EVENTS.register(name, () -> event);
    }

    public static <T extends Item> Supplier<T> registerItem(String name, T item) {
        return ITEMS.register(name, () -> item);
    }

    public static <T extends Entity, V extends EntityType<T>> Supplier<V> registerEntity(String name, V type) {
        return ENTITIES.register(name, () -> type);
    }

    public static <T extends LivingEntity> void registerEntityAttributes(Supplier<EntityType<T>> type, Supplier<AttributeSupplier.Builder> builder) {
        FMLJavaModLoadingContext.get().getModEventBus().<EntityAttributeCreationEvent>addListener(e -> e.put(type.get(), builder.get().build()));
    }

    @OnlyIn(Dist.CLIENT)
    public static <T extends Entity> void registerEntityRenderer(EntityType<T> entity, Function<EntityRenderDispatcher, EntityRenderer<T>> renderer) {
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        dispatcher.register(entity, renderer.apply(dispatcher));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerSprite(ResourceLocation sprite, ResourceLocation atlas) {
        FMLJavaModLoadingContext.get().getModEventBus().<TextureStitchEvent.Pre>addListener(e -> {
            TextureAtlas texture = e.getMap();
            if (atlas.equals(texture.location())) {
                e.addSprite(sprite);
            }
        });
    }

}
