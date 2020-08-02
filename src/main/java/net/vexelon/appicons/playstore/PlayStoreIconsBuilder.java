package net.vexelon.appicons.playstore;

import net.vexelon.appicons.AbstractAppIconsBuilder;
import net.vexelon.appicons.appstore.AppStoreDownloader;
import net.vexelon.appicons.wireframe.Downloader;

public class PlayStoreIconsBuilder extends AbstractAppIconsBuilder<AppStoreDownloader> {

    @Override
    public Downloader build() {
        return new PlayStoreDownloader(newHttpFetcher());
    }
}
