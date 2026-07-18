package xyz.amymialee.visiblebarriers.mixin.client;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.visiblebarriers.VisibleConfig;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class AbstractBlockStateMixin {
    @Shadow public abstract Block getBlock();
    @Shadow protected abstract BlockState asState();

    @Inject(method = "getRenderShape", at = @At("RETURN"), cancellable = true)
    private void visibleBarriers$invisibleModels(CallbackInfoReturnable<RenderShape> cir) {
        var block = this.getBlock();
        if (block == Blocks.BARRIER && VisibleConfig.shouldRenderBarriers()) {
            cir.setReturnValue(RenderShape.MODEL);
        } else if (block == Blocks.LIGHT && VisibleConfig.shouldRenderLights()) {
            cir.setReturnValue(RenderShape.MODEL);
        } else if (block == Blocks.STRUCTURE_VOID && VisibleConfig.shouldRenderStructureVoids()) {
            cir.setReturnValue(RenderShape.MODEL);
        } else if (block == Blocks.BUBBLE_COLUMN && VisibleConfig.shouldRenderBubbleColumns()) {
            cir.setReturnValue(RenderShape.MODEL);
        } else if (block == Blocks.MOVING_PISTON && VisibleConfig.shouldRenderMovingPistons()) {
            cir.setReturnValue(RenderShape.MODEL);
        } else if (block == Blocks.CAVE_AIR && VisibleConfig.shouldRenderCaveAir()) {
            cir.setReturnValue(RenderShape.MODEL);
        } else if (block == Blocks.VOID_AIR && VisibleConfig.shouldRenderVoidAir()) {
            cir.setReturnValue(RenderShape.MODEL);
        } else if (block instanceof WallBlock) {
            var state = this.asState();
            var east = state.getValueOrElse(WallBlock.EAST, WallSide.LOW) == WallSide.NONE;
            var west = state.getValueOrElse(WallBlock.WEST, WallSide.LOW) == WallSide.NONE;
            var north = state.getValueOrElse(WallBlock.NORTH, WallSide.LOW) == WallSide.NONE;
            var south = state.getValueOrElse(WallBlock.SOUTH, WallSide.LOW) == WallSide.NONE;

            if (east && west && north && south && !state.getValueOrElse(WallBlock.UP, false)) {
                cir.setReturnValue(VisibleConfig.shouldRenderInvisibleWalls() ? RenderShape.MODEL : RenderShape.INVISIBLE);
            }
        }
    }
}
