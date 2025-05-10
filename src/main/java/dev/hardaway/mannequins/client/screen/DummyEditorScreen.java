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
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Rotations;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public abstract class DummyEditorScreen extends AbstractContainerScreen<DummyEditorMenu> {
    private static final ResourceLocation HEAD_BUTTON_SPRITE = Mannequins.path("container/editor/head_button");
    private static final ResourceLocation HEAD_BUTTON_HIGHLIGHTED_SPRITE = Mannequins.path("container/editor/head_button_highlighted");
    private static final ResourceLocation BODY_BUTTON_SPRITE = Mannequins.path("container/editor/body_button");
    private static final ResourceLocation BODY_BUTTON_HIGHLIGHTED_SPRITE = Mannequins.path("container/editor/body_button_highlighted");
    private static final ResourceLocation ARM_BUTTON_SPRITE = Mannequins.path("container/editor/arm_button");
    private static final ResourceLocation ARM_BUTTON_HIGHLIGHTED_SPRITE = Mannequins.path("container/editor/arm_button_highlighted");

    private static final WidgetSprites RESET_BUTTON_SPRITES = new WidgetSprites(
            Mannequins.path("container/editor/reset"),
            Mannequins.path("container/editor/reset")
    );

    private static final WidgetSprites RANDOMIZE_BUTTON_SPRITES = new WidgetSprites(
            Mannequins.path("container/editor/randomize"),
            Mannequins.path("container/editor/randomize")
    );

    private static final Vector3f MODEL_TRANSLATION = new Vector3f(0, 8.0F / 28, 0);
    private static final Quaternionf MODEL_ANGLE = new Quaternionf().rotationXYZ(0, (float) Math.toRadians(180), Mth.PI);

    private final DummyBlockEntity dummy;
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

        this.addRenderableWidget(new ImageButton(this.leftPos + 137, this.topPos + 21, 11, 11, RANDOMIZE_BUTTON_SPRITES, (b) -> {
            PacketDistributor.sendToServer(new ServerboundMannequinActionPayload(this.menu.containerId, ServerboundMannequinActionPayload.Action.RANDOMIZE));
        }));
        this.addRenderableWidget(new ImageButton(this.leftPos + 154, this.topPos + 21, 11, 11, RESET_BUTTON_SPRITES, (b) -> {
            PacketDistributor.sendToServer(new ServerboundMannequinActionPayload(this.menu.containerId, ServerboundMannequinActionPayload.Action.RESET));
        }));

        this.addRenderableWidget(this.xScroll = new ScrollBar(this.leftPos + 136, this.topPos + 36, 8, 49, 360, Component.literal("X"), this::applyRotations));
        this.addRenderableWidget(this.yScroll = new ScrollBar(this.leftPos + 147, this.topPos + 36, 8, 49, 360, Component.literal("Y"), this::applyRotations));
        this.addRenderableWidget(this.zScroll = new ScrollBar(this.leftPos + 158, this.topPos + 36, 8, 49, 360, Component.literal("Z"), this::applyRotations));
        this.xScroll.setScrollSpeed(1.0F);
        this.yScroll.setScrollSpeed(1.0F);
        this.zScroll.setScrollSpeed(1.0F);
        this.updateSliders();
    }

    private void updateSliders() {
        Rotations rotations = this.getSelectedPart().getRotation(this);
        this.xScroll.setScroll(convertScroll(rotations.getX(), false, this.xScroll));
        this.yScroll.setScroll(convertScroll(rotations.getY(), false, this.yScroll));
        this.zScroll.setScroll(convertScroll(rotations.getZ(), this.getSelectedPart() == Part.LEFT_ARM, this.zScroll));
    }

    private double convertScroll(double rotation, boolean mirror, ScrollBar bar) {
        double newScroll = Mth.wrapDegrees(rotation) + 180;
        if (mirror)
            newScroll = 360 - newScroll;
        return newScroll % 360 / 360.0F * bar.getMaxScroll();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Part part : Part.values()) {
            if (this.getSelectedPart() != part && part.isHovered(mouseX - this.leftPos, mouseY - this.topPos)) {
                this.dummy.getDummy().selectedPart = part;
                this.updateSliders();
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
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

        Rotations rotations = this.getSelectedPart().getRotation(this);
        double x = rotations.getX();
        double y = rotations.getY();
        double z = rotations.getZ();

        float scrollX = this.getRotation(this.xScroll);
        float scrollY = this.getRotation(this.yScroll);
        float scrollZ = this.getRotation(this.zScroll);

//        if (x != `scrollX ||
//                y != scrollY ||
//                z != scrollZ
//        ) {
        this.updateSliders();
//        }`
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(this.getBgTextureLocation(), this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        for (Part part : Part.values()) {
            if (this.getSelectedPart() != part) {
                guiGraphics.blitSprite(part.isHovered(mouseX - this.leftPos, mouseY - this.topPos) ? part.buttonHighlightedSprite : part.buttonSprite, this.leftPos + part.xOffset, this.topPos + part.yOffset, part.buttonWidth, part.buttonHeight);
            }
        }

        int scissorX = this.leftPos + 26;
        int scissorY = this.topPos + 18;
        guiGraphics.enableScissor(scissorX, scissorY, scissorX + 49, scissorY + 70);
        InventoryScreen.renderEntityInInventory(guiGraphics, this.leftPos + 51, this.topPos + 80, 28, MODEL_TRANSLATION, MODEL_ANGLE, null, this.dummy.getDummy());
        guiGraphics.disableScissor();
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
        Rotations currentRot = this.getSelectedPart().rotationGetter.apply(this);
        if (this.xScroll.isHovered()) {
            guiGraphics.renderTooltip(this.font, Component.literal(String.valueOf(currentRot.getX()) + '°'), x, y);
        } else if (this.yScroll.isHovered()) {
            guiGraphics.renderTooltip(this.font, Component.literal(String.valueOf(currentRot.getY()) + '°'), x, y);
        } else if (this.zScroll.isHovered()) {
            guiGraphics.renderTooltip(this.font, Component.literal(String.valueOf(currentRot.getZ()) + '°'), x, y);
        }

        for (Part part : Part.values()) {
            if (this.getSelectedPart() != part && part.isHovered(x - this.leftPos, y - this.topPos)) {
                guiGraphics.renderTooltip(this.font, Component.literal(part.name()), x, y);
            }
        }

        super.renderTooltip(guiGraphics, x, y);
    }

    @Override
    public void onClose() {
        PacketDistributor.sendToServer(new ServerboundSetMannequinPosePayload(this.menu.containerId, this.dummy.getPose()));
        super.onClose();
    }

    private Part getSelectedPart() {
        return this.dummy.getDummy().selectedPart;
    }

    private float getRotation(ScrollBar bar) {
        return bar.getInterpolatedScrollPercentage(1.0F) * 360.0F - 180.0F;
    }

    private void applyPose(UnaryOperator<DummyPose> poseSetter) {
        this.dummy.setPose(poseSetter.apply(this.dummy.getPose()));
    }

    private void applyRotations(ScrollBar bar) {
        Rotations rotations = this.getSelectedPart().getRotation(this);
        double x = this.getRotation(this.xScroll);
        double y = this.getRotation(this.yScroll);
        double z = this.getRotation(this.zScroll);
        if (this.getSelectedPart() == Part.LEFT_ARM)
            z *= -1;
        if (rotations.getX() != x || rotations.getY() != y || rotations.getZ() != z) {
            this.getSelectedPart().setRotation(this, new Rotations((float) x, (float) y, (float) z)); // TODO: on scroll set rotation
            this.updateSliders();
        }
    }

    private DummyPose getPose() {
        return dummy.getPose();
    }

    // TODO clean this up
    public enum Part {
        HEAD((s, r) -> s.applyPose(p -> p.withHeadPose(r)), s -> s.getPose().head(), 98, 21, HEAD_BUTTON_SPRITE, HEAD_BUTTON_HIGHLIGHTED_SPRITE, 16, 16),
        CHEST((s, r) -> s.applyPose(p -> p.withBodyPose(r)), s -> s.getPose().body(), 98, 37, BODY_BUTTON_SPRITE, BODY_BUTTON_HIGHLIGHTED_SPRITE, 16, 20),
        LEFT_ARM((s, r) -> s.applyPose(p -> p.withLeftArmPose(r)), s -> s.getPose().leftArm(), 114, 37, ARM_BUTTON_SPRITE, ARM_BUTTON_HIGHLIGHTED_SPRITE, 8, 24),
        RIGHT_ARM((s, r) -> s.applyPose(p -> p.withRightAmPose(r)), s -> s.getPose().rightArm(), 90, 37, ARM_BUTTON_SPRITE, ARM_BUTTON_HIGHLIGHTED_SPRITE, 8, 24);

        private final BiConsumer<DummyEditorScreen, Rotations> rotationSetter;
        private final Function<DummyEditorScreen, Rotations> rotationGetter;
        private final int xOffset;
        private final int yOffset;
        private final ResourceLocation buttonSprite;
        private final ResourceLocation buttonHighlightedSprite;
        private final int buttonWidth;
        private final int buttonHeight;

        Part(BiConsumer<DummyEditorScreen, Rotations> rotationSetter, Function<DummyEditorScreen, Rotations> rotationGetter, int xOffset, int yOffset, ResourceLocation buttonSprite, ResourceLocation buttonHighlightedSprite, int buttonWidth, int buttonHeight) {
            this.rotationSetter = rotationSetter;
            this.rotationGetter = rotationGetter;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.buttonSprite = buttonSprite;
            this.buttonHighlightedSprite = buttonHighlightedSprite;
            this.buttonWidth = buttonWidth;
            this.buttonHeight = buttonHeight;
        }

        public boolean isHovered(double mouseX, double mouseY) {
            return mouseX >= this.xOffset && mouseX < this.xOffset + this.buttonWidth && mouseY >= this.yOffset && mouseY < this.yOffset + this.buttonHeight;
        }

        public Rotations getRotation(DummyEditorScreen editor) {
            return this.rotationGetter.apply(editor);
        }

        public void setRotation(DummyEditorScreen editor, Rotations rot) {
            this.rotationSetter.accept(editor, rot);
        }
    }
}