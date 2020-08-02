package net.vexelon.appicons.playstore;

import net.vexelon.appicons.AbstractAppIconsBuilder;
import net.vexelon.appicons.appstore.AppStoreDownloader;
import net.vexelon.appicons.wireframe.Downloader;

import java.util.ArrayList;
import java.util.List;

public class PlayStoreIconsBuilder extends AbstractAppIconsBuilder<PlayStoreIconsBuilder> {

    class PlayStoreConfig {

        private List<Integer> sizes = new ArrayList<>();

        public List<Integer> getSizes() {
            return sizes;
        }
    }

    private PlayStoreConfig config = new PlayStoreConfig();

    @Override
    public Downloader build() {
        return new PlayStoreDownloader(newHttpFetcher(), config);
    }

    public PlayStoreIconsBuilder sizes(int... sizes) {
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
