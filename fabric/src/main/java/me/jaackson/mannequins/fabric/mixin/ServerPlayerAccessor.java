package me.jaackson.mannequins.fabric.mixin;

import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * @author Jackson
 */
@Mixin(ServerPlayer.class)
public interface ServerPlayerAccessor {
    @Accessor
    int getContainerCounter();

    @Invoker
    void callNextContainerCounter();
}
