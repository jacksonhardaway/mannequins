package gg.moonflower.mannequins.client.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HumanoidModel.class)
public interface HumanoidModelAccessor {

    @Mutable
    @Accessor
    void setHead(ModelPart part);

    @Mutable
    @Accessor
    void setBody(ModelPart part);

    @Mutable
    @Accessor
    void setLeftArm(ModelPart part);

    @Mutable
    @Accessor
    void setRightArm(ModelPart part);
}
