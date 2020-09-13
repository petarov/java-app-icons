package net.vexelon.appicons.appstore;

import net.vexelon.appicons.AbstractDownloader;
import net.vexelon.appicons.utils.AppURLUtils;
import net.vexelon.appicons.wireframe.entities.IconURL;

import java.io.InputStream;
import java.util.List;

public class AppStoreDownloader extends AbstractDownloader<AppStoreBuilder.AppStoreConfig> {

    public AppStoreDownloader(AppStoreBuilder.AppStoreConfig config) {
        super(config);
    }

    @Override
    protected String getAppUrl(String appId) {
        return AppURLUtils.appstore(appId, config.getCountry(), config.getLanguage());
    }

    @Override
    protected List<IconURL> parse(InputStream input) {
        return new AppStoreParser(config).parse(input);
    }
}
