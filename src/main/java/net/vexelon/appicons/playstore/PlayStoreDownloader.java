package net.vexelon.appicons.playstore;

import net.vexelon.appicons.AbstractDownloader;
import net.vexelon.appicons.utils.AppURLUtils;
import net.vexelon.appicons.wireframe.entities.IconURL;

import java.io.InputStream;
import java.util.List;

public class PlayStoreDownloader extends AbstractDownloader<PlayStoreBuilder.PlayStoreConfig> {

    public PlayStoreDownloader(PlayStoreBuilder.PlayStoreConfig config) {
        super(config);
    }

    @Override
    protected String getAppUrl(String appId) {
        return AppURLUtils.playstore(appId, config.getCountry(), config.getLanguage());
    }

    @Override
    protected List<IconURL> parse(InputStream input) {
        return new PlayStoreParser(config).parse(input);
    }
}
