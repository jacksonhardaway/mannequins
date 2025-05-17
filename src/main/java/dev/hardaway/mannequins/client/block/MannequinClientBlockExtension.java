package dev.hardaway.mannequins.client.block;

import dev.hardaway.mannequins.client.model.DummyModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;

import java.util.function.Function;

public interface MannequinClientBlockExtension extends IClientBlockExtensions {

    Function<EntityRendererProvider.Context, DummyModel> getRenderingDelegate();
}
