package dev.hardaway.mannequins.client.screen;

import dev.hardaway.mannequins.api.MannequinPose;
import dev.hardaway.mannequins.client.screen.widget.ScrollBar;
import dev.hardaway.mannequins.common.block.entity.MannequinBlockEntity;
import dev.hardaway.mannequins.common.menu.MannequinMenu;
import dev.hardaway.mannequins.common.network.payload.ServerboundSetMannequinPosePayload;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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

public class MannequinScreen extends AbstractContainerScreen<MannequinMenu> {
    private static final ResourceLocation BG_LOCATION = Mannequins.path("textures/gui/container/mannequin.png");
    private static final ResourceLocation MANNEQUIN_HEAD_SPRITE = Mannequins.path("container/mannequin/mannequin_head");
    private static final ResourceLocation MANNEQUIN_HEAD_HIGHLIGHTED_SPRITE = Mannequins.path("container/mannequin/mannequin_head_highlighted");
    private static final ResourceLocation MANNEQUIN_BODY_SPRITE = Mannequins.path("container/mannequin/mannequin_body");
    private static final ResourceLocation MANNEQUIN_BODY_HIGHLIGHTED_SPRITE = Mannequins.path("container/mannequin/mannequin_body_highlighted");
    private static final ResourceLocation MANNEQUIN_ARM_SPRITE = Mannequins.path("container/mannequin/mannequin_arm");
    private static final ResourceLocation MANNEQUIN_ARM_HIGHLIGHTED_SPRITE = Mannequins.path("container/mannequin/mannequin_arm_highlighted");

    private static final Vector3f MANNEQUIN_TRANSLATION = new Vector3f(0, 8.0F / 28, 0);
    private static final Quaternionf MANNEQUIN_ANGLE = new Quaternionf().rotationXYZ(0, (float) Math.toRadians(180), Mth.PI);

    private static MannequinPart selectedPart = MannequinPart.HEAD;

    private final MannequinBlockEntity mannequin;
    private ScrollBar xScroll;
    private ScrollBar yScroll;
    private ScrollBar zScroll;

    public MannequinScreen(MannequinMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 185;
        this.inventoryLabelY += 20;

        this.mannequin = menu.getMannequin();
    }

    @Override
    protected void init() {
        super.init();

        this.addRenderableWidget(this.xScroll = new ScrollBar(this.leftPos + 136, this.topPos + 20, 8, 65, 360, Component.literal("X")));
        this.addRenderableWidget(this.yScroll = new ScrollBar(this.leftPos + 147, this.topPos + 20, 8, 65, 360, Component.literal("Y")));
        this.addRenderableWidget(this.zScroll = new ScrollBar(this.leftPos + 158, this.topPos + 20, 8, 65, 360, Component.literal("Z")));
        this.xScroll.setScrollSpeed(1);
        this.yScroll.setScrollSpeed(1);
        this.zScroll.setScrollSpeed(1);
        this.updateSliders();
    }

    private void updateSliders() {
        Rotations rotations = selectedPart.getRotation(this);
        float rotationX = Mth.wrapDegrees(rotations.getX()) + 180;
        float rotationY = Mth.wrapDegrees(rotations.getY()) + 180;
        float rotationZ = Mth.wrapDegrees(rotations.getZ()) + 180;
        if (selectedPart == MannequinPart.LEFT_ARM)
            rotationZ = 360 - rotationZ;
        this.xScroll.setScroll(rotationX % 360 / 360.0F * this.xScroll.getMaxScroll());
        this.yScroll.setScroll(rotationY % 360 / 360.0F * this.yScroll.getMaxScroll());
        this.zScroll.setScroll(rotationZ % 360 / 360.0F * this.zScroll.getMaxScroll());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (MannequinPart part : MannequinPart.values()) {
            if (selectedPart != part && part.isHovered(mouseX - this.leftPos, mouseY - this.topPos)) {
                selectedPart = part;
                this.updateSliders();
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        return this.xScroll.mouseReleased(mouseX, mouseY, mouseButton) || this.yScroll.mouseReleased(mouseX, mouseY, mouseButton) || this.zScroll.mouseReleased(mouseX, mouseY, mouseButton) || super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void containerTick() {
        this.xScroll.tick();
        this.yScroll.tick();
        this.zScroll.tick();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(BG_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        for (MannequinPart part : MannequinPart.values()) {
            if (selectedPart != part) {
                guiGraphics.blitSprite(part.isHovered(mouseX - this.leftPos, mouseY - this.topPos) ? part.buttonHighlightedSprite : part.buttonSprite, this.leftPos + part.xOffset, this.topPos + part.yOffset, part.buttonWidth, part.buttonHeight);
            }
        }

        int scissorX = this.leftPos + 26;
        int scissorY = this.topPos + 18;
        guiGraphics.enableScissor(scissorX, scissorY, scissorX + 49, scissorY + 70);
        InventoryScreen.renderEntityInInventory(guiGraphics, this.leftPos + 51, this.topPos + 80, 28, MANNEQUIN_TRANSLATION, MANNEQUIN_ANGLE, null, this.mannequin.getDummy());
        guiGraphics.disableScissor();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        Rotations rotations = selectedPart.getRotation(this);
        float x = this.getRotation(this.xScroll);
        float y = this.getRotation(this.yScroll);
        float z = this.getRotation(this.zScroll);
        if (selectedPart == MannequinPart.LEFT_ARM)
            z *= -1;
        if (rotations.getX() != x || rotations.getY() != y || rotations.getZ() != z)
            selectedPart.setRotation(this, new Rotations(x, y, z));
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        if (this.xScroll.isHovered()) {
            guiGraphics.renderTooltip(this.font, Component.literal(String.valueOf(Math.round(this.getRotation(this.xScroll))) + '°'), x, y);
        } else if (this.yScroll.isHovered()) {
            guiGraphics.renderTooltip(this.font, Component.literal(String.valueOf(Math.round(this.getRotation(this.yScroll))) + '°'), x, y);
        } else if (this.zScroll.isHovered()) {
            guiGraphics.renderTooltip(this.font, Component.literal(String.valueOf(Math.round(this.getRotation(this.zScroll))) + '°'), x, y);
        }

        for (MannequinPart part : MannequinPart.values()) {
            if (selectedPart != part && part.isHovered(x - this.leftPos, y - this.topPos)) {
                guiGraphics.renderTooltip(this.font, Component.literal(part.name()), x, y);
            }
        }

        super.renderTooltip(guiGraphics, x, y);
    }

    @Override
    public void onClose() {
        if (this.menu.stillValid(Minecraft.getInstance().player)) {
            PacketDistributor.sendToServer(new ServerboundSetMannequinPosePayload(this.menu.containerId, this.mannequin.getPose()));
        }
        super.onClose();
    }

    private float getRotation(ScrollBar bar) {
        return bar.getInterpolatedScrollPercentage(1.0F) * 360.0F - 180.0F;
    }

    private void applyPose(UnaryOperator<MannequinPose> poseSetter) {
        this.mannequin.setPose(poseSetter.apply(this.mannequin.getPose()));
    }

    private MannequinPose getPose() {
        return mannequin.getPose();
    }

    enum MannequinPart {
        HEAD((s, r) -> s.applyPose(p -> p.withHeadPose(r)), s -> s.getPose().head(), 98, 21, MANNEQUIN_HEAD_SPRITE, MANNEQUIN_HEAD_HIGHLIGHTED_SPRITE, 16, 16),
        CHEST((s, r) -> s.applyPose(p -> p.withBodyPose(r)), s -> s.getPose().body(), 98, 37, MANNEQUIN_BODY_SPRITE, MANNEQUIN_BODY_HIGHLIGHTED_SPRITE, 16, 20),
        LEFT_ARM((s, r) -> s.applyPose(p -> p.withLeftArmPose(r)), s -> s.getPose().leftArm(), 114, 37, MANNEQUIN_ARM_SPRITE, MANNEQUIN_ARM_HIGHLIGHTED_SPRITE, 8, 24),
        RIGHT_ARM((s, r) -> s.applyPose(p -> p.withRightAmPose(r)), s -> s.getPose().rightArm(), 90, 37, MANNEQUIN_ARM_SPRITE, MANNEQUIN_ARM_HIGHLIGHTED_SPRITE, 8, 24);

        private final BiConsumer<MannequinScreen, Rotations> rotationSetter;
        private final Function<MannequinScreen, Rotations> rotationGetter;
        private final int xOffset;
        private final int yOffset;
        private final ResourceLocation buttonSprite;
        private final ResourceLocation buttonHighlightedSprite;
        private final int buttonWidth;
        private final int buttonHeight;

        MannequinPart(BiConsumer<MannequinScreen, Rotations> rotationSetter, Function<MannequinScreen, Rotations> rotationGetter, int xOffset, int yOffset, ResourceLocation buttonSprite, ResourceLocation buttonHighlightedSprite, int buttonWidth, int buttonHeight) {
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

        public Rotations getRotation(MannequinScreen mannequin) {
            return this.rotationGetter.apply(mannequin);
        }

        public void setRotation(MannequinScreen mannequin, Rotations rot) {
            this.rotationSetter.accept(mannequin, rot);
        }
    }
}