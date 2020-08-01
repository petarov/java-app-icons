package net.vexelon.appicons;

import net.vexelon.appicons.appstore.AppStoreIconsBuilder;
import net.vexelon.appicons.playstore.PlayStoreIconsBuilder;

public final class AppIcons {

    /**
     * @return New App Store icons download builder instance.
     */
    public static AppStoreIconsBuilder appstore() {
        return new AppStoreIconsBuilder();
    }

    /**
     * @return New Playstore icons download builder instance.
     */
    public static PlayStoreIconsBuilder playstore() {
        return new PlayStoreIconsBuilder();
    }
}
