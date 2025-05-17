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

public record DummyExpression(Holder<Block> mannequin, ResourceLocation asset, HolderSet<Item> item) {

    public static final Codec<DummyExpression> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryFixedCodec.create(Registries.BLOCK).fieldOf("mannequin_type").forGetter(DummyExpression::mannequin),
            ResourceLocation.CODEC.fieldOf("asset_id").forGetter(DummyExpression::asset),
            RegistryCodecs.homogeneousList(Registries.ITEM).optionalFieldOf("item", HolderSet.empty()).forGetter(DummyExpression::item)
    ).apply(instance, DummyExpression::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, DummyExpression> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.BLOCK),
            DummyExpression::mannequin,
            ResourceLocation.STREAM_CODEC,
            DummyExpression::asset,
            ByteBufCodecs.holderSet(Registries.ITEM),
            DummyExpression::item,
            DummyExpression::new
    );
    public static final Codec<Holder<DummyExpression>> CODEC = RegistryFileCodec.create(MannequinsRegistries.EXPRESSIONS, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<DummyExpression>> STREAM_CODEC = ByteBufCodecs.holder(
            MannequinsRegistries.EXPRESSIONS, DIRECT_STREAM_CODEC
    );

    public DummyExpression(Holder<Block> mannequin, ResourceLocation asset) {
        this(mannequin, asset, HolderSet.empty());
    }
}
