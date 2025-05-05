package dev.hardaway.mannequins.core;

import dev.hardaway.mannequins.common.entity.Dummy;
import dev.hardaway.mannequins.common.network.handler.MannequinsServerPlayHandler;
import dev.hardaway.mannequins.common.network.payload.ServerboundSetMannequinPosePayload;
import dev.hardaway.mannequins.core.data.MannequinsBlockStateProvider;
import dev.hardaway.mannequins.core.data.MannequinsItemModelProvider;
import dev.hardaway.mannequins.core.data.MannequinsLanguageProvider;
import dev.hardaway.mannequins.core.data.MannequinsSoundDefinitionsProvider;
import dev.hardaway.mannequins.core.data.loot.MannequinsBlockLootProvider;
import dev.hardaway.mannequins.core.data.loot.MannequinsLootProvider;
import dev.hardaway.mannequins.core.registry.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod(Mannequins.MOD_ID)
public class Mannequins {

    public static final String MOD_ID = "mannequins";

    public Mannequins(IEventBus bus) {
        bus.addListener(this::setup);
        bus.addListener(this::registerAttributes);
        bus.addListener(this::registerCreativeTabs);
        bus.addListener(this::registerPayloadHandlers);
        bus.addListener(this::gatherData);

        MannequinsComponents.REGISTRY.register(bus);
        MannequinsSounds.REGISTRY.register(bus);
        MannequinsBlocks.REGISTRY.register(bus);
        MannequinsBlockEntities.REGISTRY.register(bus);
        MannequinsItems.REGISTRY.register(bus);
        MannequinsEntities.REGISTRY.register(bus);
        MannequinsMenus.REGISTRY.register(bus);
    }

    public static ResourceLocation path(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(MannequinsEntities.DUMMY.get(), Dummy.createAttributes().build());
    }

    private void registerCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.FUNCTIONAL_BLOCKS)
            return;

        event.insertAfter(new ItemStack(Items.ARMOR_STAND), new ItemStack(MannequinsItems.MANNEQUIN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("m2");
        registrar.playToServer(ServerboundSetMannequinPosePayload.PACKET_TYPE, ServerboundSetMannequinPosePayload.CODEC, new MainThreadPayloadHandler<>(MannequinsServerPlayHandler::handleSyncMannequinPose));
    }

    private void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        PackOutput packOutput = generator.getPackOutput();
        generator.addProvider(event.includeClient(), new MannequinsLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new MannequinsSoundDefinitionsProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new MannequinsBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new MannequinsItemModelProvider(packOutput, existingFileHelper));

        generator.addProvider(event.includeServer(), new MannequinsLootProvider(packOutput, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(MannequinsBlockLootProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
    }
}
