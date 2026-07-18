package xyz.amymialee.visiblebarriers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import xyz.amymialee.visiblebarriers.common.VisibleBarriersCommon;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class VisibleConfig {
    private static final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("visiblebarriers.json");
    private static boolean visibleBarrier = false;
    private static boolean visibleAir = false;
    private static boolean hideParticles = true;
    private static boolean sendFeedback = true;
    private static boolean solidLights = false;
    private static boolean fullBright = false;
    private static boolean forcedTimeEnabled = false;
    private static boolean zoomEnabled = false;
    private static float baseZoom = 2.8f;
    private static long forcedTime = 6000;
    private static VisibleBarriers.Weather weather = VisibleBarriers.Weather.DEFAULT;

    public static void setVisibleBarrier(boolean visibleBarrier) {
        VisibleConfig.visibleBarrier = visibleBarrier;
    }

    public static void setVisibleAir(boolean visibleAir) {
        VisibleConfig.visibleAir = visibleAir;
    }

    public static void setHideParticles(boolean hideParticles) {
        VisibleConfig.hideParticles = hideParticles;
    }

    public static void setSendFeedback(boolean sendFeedback) {
        VisibleConfig.sendFeedback = sendFeedback;
    }

    public static void setSolidLights(boolean solidLights) {
        VisibleConfig.solidLights = solidLights;
    }

    public static void setFullBright(boolean fullBright) {
        VisibleConfig.fullBright = fullBright;
    }

    public static void setForcedTimeEnabled(boolean forcedTimeEnabled) {
        VisibleConfig.forcedTimeEnabled = forcedTimeEnabled;
    }

    public static void setZoomEnabled(boolean zoomEnabled) {
        VisibleConfig.zoomEnabled = zoomEnabled;
    }

    public static void setBaseZoom(float baseZoom) {
        VisibleConfig.baseZoom = baseZoom;
    }

    public static void setForcedTime(long forcedTime) {
        VisibleConfig.forcedTime = forcedTime;
    }

    public static void setWeather(VisibleBarriers.Weather weather) {
        VisibleConfig.weather = weather;
    }

    public static boolean isBarrierVisible() {
        return visibleBarrier;
    }

    public static boolean isAirVisible() {
        return visibleAir;
    }

    public static boolean shouldHideParticles() {
        return hideParticles;
    }

    public static boolean shouldSendFeedback() {
        return sendFeedback;
    }

    public static float getBaseZoom() {
        return baseZoom;
    }

    public static long getForcedTime() {
        return forcedTime;
    }

    public static boolean areLightsSolid() {
        return solidLights;
    }

    public static boolean isFullBrightEnabled() {
        return fullBright;
    }

    public static boolean isForcedTimeEnabled() {
        return forcedTimeEnabled;
    }

    public static boolean isZoomEnabled() {
        return zoomEnabled;
    }

    public static VisibleBarriers.Weather getWeather() {
        return weather;
    }

    protected static void saveConfig() {
        try {
            var gson = new GsonBuilder().setPrettyPrinting().create();
            var json = new JsonObject();
            json.addProperty("visibleBarrier", visibleBarrier);
            json.addProperty("visibleAir", visibleAir);
            json.addProperty("hideParticles", hideParticles);
            json.addProperty("sendFeedback", sendFeedback);
            json.addProperty("baseZoom", baseZoom);
            json.addProperty("solidLights", solidLights);
            json.addProperty("fullBright", fullBright);
            json.addProperty("forcedTimeEnabled", forcedTimeEnabled);
            json.addProperty("forcedTime", forcedTime);
            json.addProperty("weather", weather.name());
            json.addProperty("zoomEnabled", zoomEnabled);
            var jsonData = gson.toJson(json);
            Files.writeString(configFile, jsonData);
        } catch (Exception e) {
            VisibleBarriersCommon.LOGGER.info(e.toString());
        }
    }

    protected static void loadConfig() {
        try {
            var gson = new Gson();
            var reader = Files.readString(configFile);
            var data = gson.fromJson(reader, JsonObject.class);
            if (data.has("visibleBarrier")) {
                visibleBarrier = data.get("visibleBarrier").getAsBoolean();
                // Loading must be idempotent; toggling would invert the state on every reload.
                VisibleBarriers.toggleBarriers = visibleBarrier;
            }
            if (data.has("visibleAir")) {
                visibleAir = data.get("visibleAir").getAsBoolean();
            }
            if (data.has("hideParticles")) {
                hideParticles = data.get("hideParticles").getAsBoolean();
            }
            if (data.has("sendFeedback")) {
                sendFeedback = data.get("sendFeedback").getAsBoolean();
            }
            if (data.has("baseZoom")) {
                baseZoom = data.get("baseZoom").getAsFloat();
            }
            if (data.has("solidLights")) {
                solidLights = data.get("solidLights").getAsBoolean();
            }
            if (data.has("fullBright")) {
                fullBright = data.get("fullBright").getAsBoolean();
            }
            if (data.has("forcedTimeEnabled")) {
                forcedTimeEnabled = data.get("forcedTimeEnabled").getAsBoolean();
            }
            if (data.has("forcedTime")) {
                forcedTime = data.get("forcedTime").getAsLong();
            }
            if (data.has("weather")) {
                try {
                    weather = VisibleBarriers.Weather.valueOf(data.get("weather").getAsString());
                } catch (IllegalArgumentException ignored) {
                    weather = VisibleBarriers.Weather.DEFAULT;
                }
            }
            if (data.has("zoomEnabled")) {
                zoomEnabled = data.get("zoomEnabled").getAsBoolean();
            }
        } catch (NoSuchFileException e) {
            VisibleBarriersCommon.LOGGER.info("Config data not found.");
        } catch (Exception e) {
            VisibleBarriersCommon.LOGGER.info("Error loading config data.");
            VisibleBarriersCommon.LOGGER.info(e.toString());
        }
        applyRuntimeState();
    }

    protected static void applyRuntimeState() {
        VisibleBarriers.toggleBarriers = visibleBarrier;
        VisibleBarriers.toggleFullBright = fullBright;
        VisibleBarriers.toggleTime = forcedTimeEnabled;
        VisibleBarriers.holdingZoom = zoomEnabled;
        VisibleBarriers.setWeather = weather;
        VisibleBarriers.zoomScroll = baseZoom;
    }
}
