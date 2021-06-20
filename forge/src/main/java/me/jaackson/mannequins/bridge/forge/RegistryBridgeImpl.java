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

/**
 * @author Jackson
 */
public class RegistryBridgeImpl {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Mannequins.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Mannequins.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Mannequins.MOD_ID);

    public static <T extends SoundEvent> Supplier<T> registerSound(String name, Supplier<T> object) {
        return SOUND_EVENTS.register(name, object);
    }

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> object) {
        return ITEMS.register(name, object);
    }

    public static <E extends Entity, T extends EntityType.Builder<E>> Supplier<EntityType<E>> registerEntity(String name, Supplier<T> object) {
        return ENTITIES.register(name, () -> object.get().build(Mannequins.MOD_ID + ":" + name));
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
