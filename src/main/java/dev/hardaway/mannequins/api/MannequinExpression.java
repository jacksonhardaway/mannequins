package dev.hardaway.mannequins.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.hardaway.mannequins.core.registry.MannequinsRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record MannequinExpression(Holder<Block> mannequin, ResourceLocation asset, HolderSet<Item> item) {

    public static final Codec<MannequinExpression> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryFixedCodec.create(Registries.BLOCK).fieldOf("mannequin_type").forGetter(MannequinExpression::mannequin),
            ResourceLocation.CODEC.fieldOf("asset_id").forGetter(MannequinExpression::asset),
            RegistryCodecs.homogeneousList(Registries.ITEM).optionalFieldOf("item", HolderSet.empty()).forGetter(MannequinExpression::item)
    ).apply(instance, MannequinExpression::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MannequinExpression> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.BLOCK),
            MannequinExpression::mannequin,
            ResourceLocation.STREAM_CODEC,
            MannequinExpression::asset,
            ByteBufCodecs.holderSet(Registries.ITEM),
            MannequinExpression::item,
            MannequinExpression::new
    );
    public static final Codec<Holder<MannequinExpression>> CODEC = RegistryFileCodec.create(MannequinsRegistries.MANNEQUIN_EXPRESSIONS, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<MannequinExpression>> STREAM_CODEC = ByteBufCodecs.holder(
            MannequinsRegistries.MANNEQUIN_EXPRESSIONS, DIRECT_STREAM_CODEC
    );
}
