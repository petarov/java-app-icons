package net.vexelon.appicons.appstore;

import net.vexelon.appicons.AbstractAppIconsBuilder;
import net.vexelon.appicons.wireframe.Downloader;

public class AppStoreIconsBuilder extends AbstractAppIconsBuilder<AppStoreDownloader> {

    @Override
    public Downloader build() {
        return new AppStoreDownloader(newHttpFetcher());
    }
}
