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
                        Component.translatable("visiblebarriers.config.visibleair"),
                        VisibleConfig.isAirVisible())
                .setDefaultValue(false)
                .setSaveConsumer(VisibleConfig::setVisibleAir)
                .build());
        rendering.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.solidlights"),
                        VisibleConfig.areLightsSolid())
                .setDefaultValue(false)
                .setSaveConsumer(VisibleConfig::setSolidLights)
                .build());
        rendering.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.hideparticles"),
                        VisibleConfig.shouldHideParticles())
                .setDefaultValue(true)
                .setSaveConsumer(VisibleConfig::setHideParticles)
                .build());

        var client = builder.getOrCreateCategory(Component.translatable("visiblebarriers.config.category.client"));
        client.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.fullbright"),
                        VisibleConfig.isFullBrightEnabled())
                .setDefaultValue(false)
                .setSaveConsumer(VisibleConfig::setFullBright)
                .build());
        client.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.forcedtime"),
                        VisibleConfig.isForcedTimeEnabled())
                .setDefaultValue(false)
                .setSaveConsumer(VisibleConfig::setForcedTimeEnabled)
                .build());
        client.addEntry(entries.startLongField(
                        Component.translatable("visiblebarriers.config.time"),
                        VisibleConfig.getForcedTime())
                .setDefaultValue(6000L)
                .setMin(0L)
                .setMax(24000L)
                .setSaveConsumer(VisibleConfig::setForcedTime)
                .build());
        client.addEntry(entries.startEnumSelector(
                        Component.translatable("visiblebarriers.config.weather"),
                        VisibleBarriers.Weather.class,
                        VisibleConfig.getWeather())
                .setDefaultValue(VisibleBarriers.Weather.DEFAULT)
                .setEnumNameProvider(value -> Component.translatable(((VisibleBarriers.Weather) value).getTranslationKey()))
                .setSaveConsumer(VisibleConfig::setWeather)
                .build());
        client.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.zoom"),
                        VisibleConfig.isZoomEnabled())
                .setDefaultValue(false)
                .setSaveConsumer(VisibleConfig::setZoomEnabled)
                .build());
        client.addEntry(entries.startFloatField(
                        Component.translatable("visiblebarriers.config.basezoom"),
                        VisibleConfig.getBaseZoom())
                .setDefaultValue(2.8F)
                .setMin(0.01F)
                .setMax(1000F)
                .setSaveConsumer(VisibleConfig::setBaseZoom)
                .build());
        client.addEntry(entries.startBooleanToggle(
                        Component.translatable("visiblebarriers.config.sendfeedback"),
                        VisibleConfig.shouldSendFeedback())
                .setDefaultValue(true)
                .setSaveConsumer(VisibleConfig::setSendFeedback)
                .build());

        builder.setSavingRunnable(() -> {
            VisibleConfig.applyRuntimeState();
            VisibleConfig.saveConfig();
            VisibleBarriers.reloadWorldRenderer();
        });
        return builder.build();
    }
}
