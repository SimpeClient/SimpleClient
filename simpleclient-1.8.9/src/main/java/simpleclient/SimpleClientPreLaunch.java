package simpleclient;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import simpleclient.feature.FeatureManager;
import simpleclient.feature.FeatureManagerImpl;

public class SimpleClientPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        FeatureManager.INSTANCE = new FeatureManagerImpl();
    }
}