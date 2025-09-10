package dev.hardaway.mannequins.client.screen;

import dev.hardaway.mannequins.api.DummyPose;
import dev.hardaway.mannequins.client.screen.widget.ScrollBar;
import dev.hardaway.mannequins.common.block.entity.DummyBlockEntity;
import dev.hardaway.mannequins.common.menu.DummyEditorMenu;
import dev.hardaway.mannequins.common.network.payload.ServerboundMannequinActionPayload;
import dev.hardaway.mannequins.common.network.payload.ServerboundSetMannequinPosePayload;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.Rotations;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public abstract class DummyEditorScreen extends AbstractContainerScreen<DummyEditorMenu> {


    private static final WidgetSprites RESET_BUTTON_SPRITES = new WidgetSprites(
            Mannequins.path("container/editor/reset"),
            Mannequins.path("container/editor/reset")
    );

    private static final WidgetSprites RANDOMIZE_BUTTON_SPRITES = new WidgetSprites(
            Mannequins.path("container/editor/randomize"),
            Mannequins.path("container/editor/randomize")
    );

    private static final Component RANDOMIZE = Component.translatable("container." + Mannequins.MOD_ID + ".dummy_editor.randomize_pose");
    private static final Component RESET = Component.translatable("container." + Mannequins.MOD_ID + ".dummy_editor.reset_pose");


    private static final Vector3f MODEL_TRANSLATION = new Vector3f(0, 8.0F / 28, 0);
    private static final Quaternionf MODEL_ANGLE = new Quaternionf().rotationXYZ(0, (float) Math.toRadians(180), Mth.PI);

    private final DummyBlockEntity dummy;

    protected DummyPartWidget selectedPart;

    private ScrollBar xScroll;
    private ScrollBar yScroll;
    private ScrollBar zScroll;

    public DummyEditorScreen(DummyEditorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 185;
        this.inventoryLabelY += 20;

        this.dummy = menu.getDummy();
    }

    protected abstract ResourceLocation getBgTextureLocation();

    @Override
    protected void init() {
        super.init();

        ImageButton randomizeButton = new ImageButton(this.leftPos + 137, this.topPos + 21, 11, 11, DummyEditorScreen.RANDOMIZE_BUTTON_SPRITES, (b) ->
                PacketDistributor.sendToServer(new ServerboundMannequinActionPayload(this.menu.containerId, ServerboundMannequinActionPayload.Action.RANDOMIZE))
        );
        randomizeButton.setTooltip(Tooltip.create(DummyEditorScreen.RANDOMIZE));
        this.addRenderableWidget(randomizeButton);

        ImageButton resetButton = new ImageButton(this.leftPos + 154, this.topPos + 21, 11, 11, RESET_BUTTON_SPRITES, (b) ->
                PacketDistributor.sendToServer(new ServerboundMannequinActionPayload(this.menu.containerId, ServerboundMannequinActionPayload.Action.RESET))
        );
        resetButton.setTooltip(Tooltip.create(DummyEditorScreen.RESET));
        this.addRenderableWidget(resetButton);

        this.addRenderableWidget(this.xScroll = new ScrollBar(this.leftPos + 136, this.topPos + 36, 8, 49, 360, Component.literal("X"), this::applyRotations));
        this.addRenderableWidget(this.yScroll = new ScrollBar(this.leftPos + 147, this.topPos + 36, 8, 49, 360, Component.literal("Y"), this::applyRotations));
        this.addRenderableWidget(this.zScroll = new ScrollBar(this.leftPos + 158, this.topPos + 36, 8, 49, 360, Component.literal("Z"), this::applyRotations));

        // TODO: remember selected part when resizing screen
        DummyPartWidget headWidget = new DummyPartWidget(this, DummyEditorScreen.Part.HEAD, this.leftPos + 98, this.topPos + 21, 16, 16);
        this.addRenderableWidget(headWidget);

        this.addRenderableWidget(new DummyPartWidget(this, DummyEditorScreen.Part.BODY, this.leftPos + 98, this.topPos + 37, 16, 20));
        this.addRenderableWidget(new DummyPartWidget(this, DummyEditorScreen.Part.LEFT_ARM, this.leftPos + 114, this.topPos + 37, 8, 24));
        this.addRenderableWidget(new DummyPartWidget(this, DummyEditorScreen.Part.RIGHT_ARM, this.leftPos + 90, this.topPos + 37, 8, 24));

        this.xScroll.setScrollSpeed(1.0F);
        this.yScroll.setScrollSpeed(1.0F);
        this.zScroll.setScrollSpeed(1.0F);

        this.selectPart(headWidget);
    }

    private void updateSliders(Rotations rotations, boolean mirrorZ) {
        this.xScroll.setScroll(convertScroll(rotations.getX(), false, this.xScroll));
        this.yScroll.setScroll(convertScroll(rotations.getY(), false, this.yScroll));
        this.zScroll.setScroll(convertScroll(rotations.getZ(), mirrorZ, this.zScroll));
    }

    protected void selectPart(DummyPartWidget part) {
        part.active = false;
        if (this.selectedPart != null) {
            this.selectedPart.active = true;
        }

        this.selectedPart = part;

        Rotations rotations = part.part.getRotation(this);
        this.updateSliders(rotations, part.part == Part.LEFT_ARM);
    }

    private double convertScroll(double rotation, boolean mirror, ScrollBar bar) {
        double newScroll = Mth.wrapDegrees(rotation) + 180;
        if (mirror)
            newScroll = 360 - newScroll;
        return newScroll % 360 / 360.0F * bar.getMaxScroll();
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        return this.xScroll.mouseReleased(mouseX, mouseY, mouseButton) ||
                this.yScroll.mouseReleased(mouseX, mouseY, mouseButton) ||
                this.zScroll.mouseReleased(mouseX, mouseY, mouseButton) ||
                super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        this.xScroll.mouseMoved(mouseX, mouseY);
        this.yScroll.mouseMoved(mouseX, mouseY);
        this.zScroll.mouseMoved(mouseX, mouseY);
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    protected void containerTick() {
        this.xScroll.tick();
        this.yScroll.tick();
        this.zScroll.tick();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(this.getBgTextureLocation(), this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        int scissorX = this.leftPos + 26;
        int scissorY = this.topPos + 18;
        guiGraphics.pose().pushPose();
        guiGraphics.enableScissor(scissorX, scissorY, scissorX + 49, scissorY + 70);
        InventoryScreen.renderEntityInInventory(guiGraphics, this.leftPos + 51, this.topPos + 80, 28, MODEL_TRANSLATION, MODEL_ANGLE, null, this.dummy.getDummy());
        guiGraphics.disableScissor();
        guiGraphics.pose().popPose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        // TODO: cleanup rotation tooltip
        Rotations currentRot = this.selectedPart.part.getRotation(this);
        if (this.xScroll.isHovered()) {
            guiGraphics.renderTooltip(this.font, Component.literal(String.valueOf(currentRot.getX()) + '°'), x, y);
        } else if (this.yScroll.isHovered()) {
            guiGraphics.renderTooltip(this.font, Component.literal(String.valueOf(currentRot.getY()) + '°'), x, y);
        } else if (this.zScroll.isHovered()) {
            guiGraphics.renderTooltip(this.font, Component.literal(String.valueOf(currentRot.getZ()) + '°'), x, y);
        }

        super.renderTooltip(guiGraphics, x, y);
    }

    @Override
    public void onClose() {
        PacketDistributor.sendToServer(new ServerboundSetMannequinPosePayload(this.menu.containerId, this.dummy.getPose()));
        super.onClose();
    }

    private float getRotation(ScrollBar bar) {
        return bar.getInterpolatedScrollPercentage(1.0F) * 360.0F - 180.0F;
    }

    private void applyPose(UnaryOperator<DummyPose> poseSetter) {
        this.dummy.setPose(poseSetter.apply(this.dummy.getPose()));
    }

    private void applyRotations(ScrollBar bar) {
        float x = this.getRotation(this.xScroll);
        float y = this.getRotation(this.yScroll);
        float z = this.getRotation(this.zScroll);

        if (this.selectedPart.part == Part.LEFT_ARM) {
            z *= -1;
        }

        this.selectedPart.part.setRotation(this, new Rotations(x, y, z));
    }

    private DummyPose getPose() {
        return dummy.getPose();
    }

    // TODO clean this up
    public enum Part {
        HEAD(Component.translatable("container." + Mannequins.MOD_ID + ".dummy_editor.head"), (s, r) -> s.applyPose(p -> p.withHeadPose(r)), s -> s.getPose().head()),
        BODY(Component.translatable("container." + Mannequins.MOD_ID + ".dummy_editor.body"), (s, r) -> s.applyPose(p -> p.withBodyPose(r)), s -> s.getPose().body()),
        LEFT_ARM(Component.translatable("container." + Mannequins.MOD_ID + ".dummy_editor.left_arm"), (s, r) -> s.applyPose(p -> p.withLeftArmPose(r)), s -> s.getPose().leftArm()),
        RIGHT_ARM(Component.translatable("container." + Mannequins.MOD_ID + ".dummy_editor.right_arm"), (s, r) -> s.applyPose(p -> p.withRightAmPose(r)), s -> s.getPose().rightArm());

        private final Component name;
        private final BiConsumer<DummyEditorScreen, Rotations> rotationSetter;
        private final Function<DummyEditorScreen, Rotations> rotationGetter;

        Part(Component name, BiConsumer<DummyEditorScreen, Rotations> rotationSetter, Function<DummyEditorScreen, Rotations> rotationGetter) {
            this.name = name;
            this.rotationSetter = rotationSetter;
            this.rotationGetter = rotationGetter;
        }

        public Component getName() {
            return name;
        }

        public Rotations getRotation(DummyEditorScreen editor) {
            return this.rotationGetter.apply(editor);
        }

        public void setRotation(DummyEditorScreen editor, Rotations rot) {
            this.rotationSetter.accept(editor, rot);
        }
    }
}