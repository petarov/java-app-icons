package net.vexelon.appicons.appstore;

import net.vexelon.appicons.AbstractBuilder;
import net.vexelon.appicons.BuilderConfig;
import net.vexelon.appicons.wireframe.AsyncDownloader;
import net.vexelon.appicons.wireframe.SyncDownloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class AppStoreBuilder extends AbstractBuilder<AppStoreBuilder> {

    class AppStoreConfig extends BuilderConfig {

        private List<Integer> sizes = new ArrayList<>(List.of(60, 100, 512));

        public List<Integer> getSizes() {
            return sizes;
        }
    }

    private AppStoreBuilder.AppStoreConfig config = new AppStoreBuilder.AppStoreConfig();

    @Override
    public BuilderConfig config() {
        return config;
    }

    @Override
    public SyncDownloader build() {
        return new AppStoreDownloader(config);
    }

    @Override
    public AsyncDownloader buildAsync(ExecutorService executorService) {
        Objects.requireNonNull(executorService, "Async operations require an executor service!");
        config.setExecutorService(executorService);
        return new AppStoreDownloader(config);
    }

    public AppStoreBuilder size60(boolean enabled) {
        if (enabled) {
            config.getSizes().add(60);
        } else {
            config.getSizes().removeIf(v -> v == 60);
        }
        return this;
    }

    public AppStoreBuilder size100(boolean enabled) {
        if (enabled) {
            config.getSizes().add(100);
        } else {
            config.getSizes().removeIf(v -> v == 100);
        }
        return this;
    }

    public AppStoreBuilder size512(boolean enabled) {
        if (enabled) {
            config.getSizes().add(512);
        } else {
            config.getSizes().removeIf(v -> v == 512);
        }
        return this;
    }
}
