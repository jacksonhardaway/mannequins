package me.jaackson.mannequins.client.screen.forge;

import me.jaackson.mannequins.client.screen.MannequinsScreenSpriteManager;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;

/**
 * @author Jackson
 */
public class MannequinsScreenSpriteManagerImpl {
    public static void registerScreenSpriteUploader(ResourceManager resourceManager, MannequinsScreenSpriteManager.Uploader uploader) {
        if (resourceManager instanceof ReloadableResourceManager)
            ((ReloadableResourceManager) resourceManager).registerReloadListener(uploader);
    }
}
