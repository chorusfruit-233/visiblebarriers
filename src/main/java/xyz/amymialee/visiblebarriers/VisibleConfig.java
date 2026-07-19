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

    private static boolean renderBarriers = true;
    private static boolean renderLights = true;
    private static boolean renderStructureVoids = true;
    private static boolean renderBubbleColumns = true;
    private static boolean renderMovingPistons = true;
    private static boolean renderCaveAir = false;
    private static boolean renderVoidAir = false;
    private static boolean renderInvisibleWalls = true;
    private static boolean renderInvisibleEntities = true;
    private static boolean hideParticles = true;
    private static boolean solidLights = false;

    public static boolean shouldRenderBarriers() {
        return renderBarriers;
    }

    public static void setRenderBarriers(boolean renderBarriers) {
        VisibleConfig.renderBarriers = renderBarriers;
    }

    public static boolean shouldRenderLights() {
        return renderLights;
    }

    public static void setRenderLights(boolean renderLights) {
        VisibleConfig.renderLights = renderLights;
    }

    public static boolean shouldRenderStructureVoids() {
        return renderStructureVoids;
    }

    public static void setRenderStructureVoids(boolean renderStructureVoids) {
        VisibleConfig.renderStructureVoids = renderStructureVoids;
    }

    public static boolean shouldRenderBubbleColumns() {
        return renderBubbleColumns;
    }

    public static void setRenderBubbleColumns(boolean renderBubbleColumns) {
        VisibleConfig.renderBubbleColumns = renderBubbleColumns;
    }

    public static boolean shouldRenderMovingPistons() {
        return renderMovingPistons;
    }

    public static void setRenderMovingPistons(boolean renderMovingPistons) {
        VisibleConfig.renderMovingPistons = renderMovingPistons;
    }

    public static boolean shouldRenderCaveAir() {
        return renderCaveAir;
    }

    public static void setRenderCaveAir(boolean renderCaveAir) {
        VisibleConfig.renderCaveAir = renderCaveAir;
    }

    public static boolean shouldRenderVoidAir() {
        return renderVoidAir;
    }

    public static void setRenderVoidAir(boolean renderVoidAir) {
        VisibleConfig.renderVoidAir = renderVoidAir;
    }

    public static boolean shouldRenderInvisibleWalls() {
        return renderInvisibleWalls;
    }

    public static void setRenderInvisibleWalls(boolean renderInvisibleWalls) {
        VisibleConfig.renderInvisibleWalls = renderInvisibleWalls;
    }

    public static boolean shouldRenderInvisibleEntities() {
        return renderInvisibleEntities;
    }

    public static void setRenderInvisibleEntities(boolean renderInvisibleEntities) {
        VisibleConfig.renderInvisibleEntities = renderInvisibleEntities;
    }

    public static boolean shouldHideParticles() {
        return hideParticles;
    }

    public static void setHideParticles(boolean hideParticles) {
        VisibleConfig.hideParticles = hideParticles;
    }

    public static boolean areLightsSolid() {
        return solidLights;
    }

    public static void setSolidLights(boolean solidLights) {
        VisibleConfig.solidLights = solidLights;
    }

    protected static void saveConfig() {
        try {
            var gson = new GsonBuilder().setPrettyPrinting().create();
            var json = new JsonObject();
            json.addProperty("renderBarriers", renderBarriers);
            json.addProperty("renderLights", renderLights);
            json.addProperty("renderStructureVoids", renderStructureVoids);
            json.addProperty("renderBubbleColumns", renderBubbleColumns);
            json.addProperty("renderMovingPistons", renderMovingPistons);
            json.addProperty("renderCaveAir", renderCaveAir);
            json.addProperty("renderVoidAir", renderVoidAir);
            json.addProperty("renderInvisibleWalls", renderInvisibleWalls);
            json.addProperty("renderInvisibleEntities", renderInvisibleEntities);
            json.addProperty("hideParticles", hideParticles);
            json.addProperty("solidLights", solidLights);
            Files.writeString(configFile, gson.toJson(json));
        } catch (Exception e) {
            VisibleBarriersCommon.LOGGER.info(e.toString());
        }
    }

    protected static void loadConfig() {
        try {
            var data = new Gson().fromJson(Files.readString(configFile), JsonObject.class);
            if (data.has("renderBarriers")) renderBarriers = data.get("renderBarriers").getAsBoolean();
            if (data.has("renderLights")) renderLights = data.get("renderLights").getAsBoolean();
            if (data.has("renderStructureVoids")) renderStructureVoids = data.get("renderStructureVoids").getAsBoolean();
            if (data.has("renderBubbleColumns")) renderBubbleColumns = data.get("renderBubbleColumns").getAsBoolean();
            if (data.has("renderMovingPistons")) renderMovingPistons = data.get("renderMovingPistons").getAsBoolean();
            if (data.has("renderCaveAir")) renderCaveAir = data.get("renderCaveAir").getAsBoolean();
            if (data.has("renderVoidAir")) renderVoidAir = data.get("renderVoidAir").getAsBoolean();
            if (data.has("renderInvisibleWalls")) renderInvisibleWalls = data.get("renderInvisibleWalls").getAsBoolean();
            if (data.has("renderInvisibleEntities")) renderInvisibleEntities = data.get("renderInvisibleEntities").getAsBoolean();
            if (data.has("hideParticles")) hideParticles = data.get("hideParticles").getAsBoolean();
            if (data.has("solidLights")) solidLights = data.get("solidLights").getAsBoolean();
        } catch (NoSuchFileException e) {
            VisibleBarriersCommon.LOGGER.info("Config data not found.");
        } catch (Exception e) {
            VisibleBarriersCommon.LOGGER.info("Error loading config data.");
            VisibleBarriersCommon.LOGGER.info(e.toString());
        }
    }
}
