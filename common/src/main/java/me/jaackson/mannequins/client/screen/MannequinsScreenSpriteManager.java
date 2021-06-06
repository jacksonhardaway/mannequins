package me.jaackson.mannequins.client.screen;

import me.jaackson.mannequins.Mannequins;
import me.jaackson.mannequins.bridge.Platform;
import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ocelot
 */
@Environment(EnvType.CLIENT)
public final class MannequinsScreenSpriteManager {
    public static final ResourceLocation ATLAS_LOCATION = new ResourceLocation(Mannequins.MOD_ID, "textures/atlas/gui.png");
    public static final ResourceLocation UPLOADER_ID = new ResourceLocation(Mannequins.MOD_ID, "screen_sprites");
    private static Uploader spriteUploader;

    private MannequinsScreenSpriteManager() {
    }

    public static void setup() {
        Minecraft minecraft = Minecraft.getInstance();
        Uploader spriteUploader = new Uploader(minecraft.getTextureManager(), ATLAS_LOCATION, "gui");
        ResourceManager resourceManager = minecraft.getResourceManager();
        registerSprites(resourceManager, spriteUploader);
        registerScreenSpriteUploader(resourceManager, spriteUploader);
        MannequinsScreenSpriteManager.spriteUploader = spriteUploader;
    }

    private static void registerSprites(ResourceManager resourceManager, Uploader uploader) {
        uploader.registerSpriteSupplier(() -> resourceManager.listResources("textures/gui/component", s -> s.endsWith(".png")).stream().map(resourceLocation -> new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath().substring("textures/gui/".length(), resourceLocation.getPath().length() - 4))).collect(Collectors.toSet()));
    }

    @ExpectPlatform
    public static void registerScreenSpriteUploader(ResourceManager resourceManager, MannequinsScreenSpriteManager.Uploader uploader) {
        Platform.safeAssertionError();
    }

    /**
     * Checks the texture map for the specified icon.
     *
     * @param icon The key of the icon
     * @return The sprite for the specified key
     */
    public static TextureAtlasSprite getSprite(ResourceLocation icon) {
        return spriteUploader.getSprite(icon);
    }

    public static class Uploader extends TextureAtlasHolder {
        private final Set<ResourceLocation> registeredSprites;
        private final Set<Supplier<Collection<ResourceLocation>>> registeredSpriteSuppliers;

        public Uploader(TextureManager textureManager, ResourceLocation textureLocation, String prefix) {
            super(textureManager, textureLocation, prefix);
            this.registeredSprites = new HashSet<>();
            this.registeredSpriteSuppliers = new HashSet<>();
        }

        public void registerSprite(ResourceLocation location) {
            this.registeredSprites.add(location);
        }

        public void registerSpriteSupplier(Supplier<Collection<ResourceLocation>> location) {
            this.registeredSpriteSuppliers.add(location);
        }

        @Override
        protected Stream<ResourceLocation> getResourcesToLoad() {
            Set<ResourceLocation> locations = new HashSet<>(this.registeredSprites);
            this.registeredSpriteSuppliers.stream().map(Supplier::get).forEach(locations::addAll);
            return Collections.unmodifiableSet(locations).stream();
        }

        @Override
        public TextureAtlasSprite getSprite(ResourceLocation location) {
            return super.getSprite(location);
        }

        @Override
        public TextureAtlas.Preparations prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
            return super.prepare(resourceManager, profilerFiller);
        }

        @Override
        public void apply(TextureAtlas.Preparations preparations, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
            super.apply(preparations, resourceManager, profilerFiller);
        }
    }
}