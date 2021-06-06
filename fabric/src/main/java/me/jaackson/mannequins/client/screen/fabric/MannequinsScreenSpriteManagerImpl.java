package me.jaackson.mannequins.client.screen.fabric;

import me.jaackson.mannequins.client.screen.MannequinsScreenSpriteManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MannequinsScreenSpriteManagerImpl {
    public static void registerScreenSpriteUploader(ResourceManager resourceManager, MannequinsScreenSpriteManager.Uploader uploader) {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return MannequinsScreenSpriteManager.UPLOADER_ID;
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller profilerFiller, ProfilerFiller profilerFiller2, Executor executor, Executor executor2) {
                return CompletableFuture.supplyAsync(
                        () -> uploader.prepare(resourceManager, profilerFiller), executor)
                        .thenCompose(preparationBarrier::wait)
                        .thenAcceptAsync((object) -> uploader.apply(object, resourceManager, profilerFiller2), executor2);
            }
        });
    }
}
