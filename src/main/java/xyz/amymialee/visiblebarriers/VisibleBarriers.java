package xyz.amymialee.visiblebarriers;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.CustomUnbakedBlockStateModel;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.minecraft.client.Minecraft;
import xyz.amymialee.visiblebarriers.common.VisibleBarriersCommon;
import xyz.amymialee.visiblebarriers.common.VisibleBarriersNetworking;
import xyz.amymialee.visiblebarriers.model.TransparentBlockStateModel;

@Environment(EnvType.CLIENT)
public class VisibleBarriers implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VisibleConfig.loadConfig();
        ClientConfigurationNetworking.registerGlobalReceiver(VisibleBarriersNetworking.ModInstalledPayload.TYPE, (_, _) -> {});
        CustomUnbakedBlockStateModel.register(VisibleBarriersCommon.id("transparent"), TransparentBlockStateModel.Unbaked.CODEC);
    }

    public static void reloadWorldRenderer() {
        var minecraft = Minecraft.getInstance();
        if (minecraft.levelExtractor != null) {
            minecraft.levelExtractor.allChanged();
        }
    }
}
