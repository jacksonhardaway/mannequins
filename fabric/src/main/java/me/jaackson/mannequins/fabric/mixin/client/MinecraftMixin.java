package me.jaackson.mannequins.fabric.mixin.client;

import me.jaackson.mannequins.client.screen.MannequinsScreenSpriteManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/color/item/ItemColors;createDefault(Lnet/minecraft/client/color/block/BlockColors;)Lnet/minecraft/client/color/item/ItemColors;"))
    public void registerAtlas(GameConfig gameConfig, CallbackInfo ci) {
        MannequinsScreenSpriteManager.setup();
    }
}
