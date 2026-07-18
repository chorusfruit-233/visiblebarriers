package xyz.amymialee.visiblebarriers;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class VisibleConfigScreen {
    private VisibleConfigScreen() {
    }

    public static Screen create(Screen parent) {
        var builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("visiblebarriers.config.title"));
        var entries = builder.entryBuilder();

        var rendering = builder.getOrCreateCategory(Component.translatable("visiblebarriers.config.category.rendering"));
        rendering.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.renderbarriers"),
                        VisibleConfig.shouldRenderBarriers())
                .setDefaultValue(true)
                .setSaveConsumer(VisibleConfig::setRenderBarriers)
                .build());
        rendering.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.renderlights"),
                        VisibleConfig.shouldRenderLights())
                .setDefaultValue(true)
                .setSaveConsumer(VisibleConfig::setRenderLights)
                .build());
        rendering.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.renderstructurevoids"),
                        VisibleConfig.shouldRenderStructureVoids())
                .setDefaultValue(true)
                .setSaveConsumer(VisibleConfig::setRenderStructureVoids)
                .build());
        rendering.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.renderbubblecolumns"),
                        VisibleConfig.shouldRenderBubbleColumns())
                .setDefaultValue(true)
                .setSaveConsumer(VisibleConfig::setRenderBubbleColumns)
                .build());
        rendering.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.rendermovingpistons"),
                        VisibleConfig.shouldRenderMovingPistons())
                .setDefaultValue(true)
                .setSaveConsumer(VisibleConfig::setRenderMovingPistons)
                .build());
        rendering.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.rendercaveair"),
                        VisibleConfig.shouldRenderCaveAir())
                .setDefaultValue(false)
                .setSaveConsumer(VisibleConfig::setRenderCaveAir)
                .build());
        rendering.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.rendervoidair"),
                        VisibleConfig.shouldRenderVoidAir())
                .setDefaultValue(false)
                .setSaveConsumer(VisibleConfig::setRenderVoidAir)
                .build());
        rendering.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.renderinvisiblewalls"),
                        VisibleConfig.shouldRenderInvisibleWalls())
                .setDefaultValue(true)
                .setSaveConsumer(VisibleConfig::setRenderInvisibleWalls)
                .build());

        builder.setSavingRunnable(() -> {
            VisibleConfig.saveConfig();
            VisibleBarriers.reloadWorldRenderer();
        });
        return builder.build();
    }
}
