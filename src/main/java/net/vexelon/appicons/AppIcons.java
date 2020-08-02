package net.vexelon.appicons;

import net.vexelon.appicons.appstore.AppStoreBuilder;
import net.vexelon.appicons.playstore.PlayStoreBuilder;

public final class AppIcons {

    /**
     * @return New App Store icons download builder instance.
     */
    public static AppStoreBuilder appstore() {
        return new AppStoreBuilder();
    }

    /**
     * @return New Play Store icons download builder instance.
     */
    public static PlayStoreBuilder playstore() {
        return new PlayStoreBuilder();
    }
}
