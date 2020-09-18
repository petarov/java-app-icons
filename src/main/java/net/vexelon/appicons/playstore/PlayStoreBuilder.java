package net.vexelon.appicons.playstore;

import net.vexelon.appicons.AbstractBuilder;
import net.vexelon.appicons.BuilderConfig;
import net.vexelon.appicons.wireframe.AsyncDownloader;
import net.vexelon.appicons.wireframe.SyncDownloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class PlayStoreBuilder extends AbstractBuilder<PlayStoreBuilder> {

    class PlayStoreConfig extends BuilderConfig {

        private List<Integer> sizes = new ArrayList<>();

        public List<Integer> getSizes() {
            return sizes;
        }
    }

    private PlayStoreConfig config = new PlayStoreConfig();

    @Override
    public BuilderConfig config() {
        return config;
    }

    @Override
    public SyncDownloader build() {
        return new PlayStoreDownloader(config);
    }

    @Override
    public AsyncDownloader buildAsync(ExecutorService executorService) {
        Objects.requireNonNull(executorService, "Async operations require an executor service!");
        config.setExecutorService(executorService);
        return new PlayStoreDownloader(config);
    }

    public PlayStoreBuilder sizes(int... sizes) {
        for (var size : sizes) {
            if (size < 1) {
                throw new RuntimeException(size + " is not a valid image size!");
            }
            if (size > 512) {
                throw new RuntimeException("512 is the maximum size allowed");
            }
            config.getSizes().add(size);
        }
        return this;
    }
}
