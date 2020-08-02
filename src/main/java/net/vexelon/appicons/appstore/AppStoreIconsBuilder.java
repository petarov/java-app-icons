package net.vexelon.appicons.appstore;

import net.vexelon.appicons.AbstractAppIconsBuilder;
import net.vexelon.appicons.wireframe.Downloader;

import java.util.ArrayList;
import java.util.List;

public class AppStoreIconsBuilder extends AbstractAppIconsBuilder<AppStoreIconsBuilder> {

    class AppStoreConfig {

        private List<Integer> sizes = new ArrayList<>(List.of(60, 100, 512));

        public List<Integer> getSizes() {
            return sizes;
        }
    }

    private AppStoreIconsBuilder.AppStoreConfig config = new AppStoreIconsBuilder.AppStoreConfig();

    @Override
    public Downloader build() {
        return new AppStoreDownloader(newHttpFetcher(), config);
    }

    public AppStoreIconsBuilder size60(boolean enabled) {
        if (enabled) {
            config.getSizes().add(60);
        } else {
            config.getSizes().removeIf(v -> v == 60);
        }
        return this;
    }

    public AppStoreIconsBuilder size100(boolean enabled) {
        if (enabled) {
            config.getSizes().add(100);
        } else {
            config.getSizes().removeIf(v -> v == 100);
        }
        return this;
    }

    public AppStoreIconsBuilder size512(boolean enabled) {
        if (enabled) {
            config.getSizes().add(512);
        } else {
            config.getSizes().removeIf(v -> v == 512);
        }
        return this;
    }
}
