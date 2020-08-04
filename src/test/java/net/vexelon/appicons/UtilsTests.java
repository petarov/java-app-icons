package net.vexelon.appicons;

import net.vexelon.appicons.utils.AppURLUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTests {

    @Test
    void testPlayStoreLinks() {
        Assertions.assertEquals("https://play.google.com/store/apps/details?id=net.vexelon.currencybg.app",
                AppURLUtils.playstore("net.vexelon.currencybg.app", "", ""));
        Assertions.assertEquals("https://play.google.com/store/apps/details?id=net.vexelon.currencybg.app&hl=bg",
                AppURLUtils.playstore("net.vexelon.currencybg.app", "", "bg"));
        Assertions.assertEquals("https://play.google.com/store/apps/details?id=net.vexelon.currencybg.app&gl=bg",
                AppURLUtils.playstore("net.vexelon.currencybg.app", "bg", ""));
        Assertions.assertEquals("https://play.google.com/store/apps/details?id=net.vexelon.currencybg.app&gl=bg&hl=bg",
                AppURLUtils.playstore("net.vexelon.currencybg.app", "bg", "bg"));

    }

    @Test
    void testAppStoreLinks() {
        Assertions.assertEquals("https://itunes.apple.com/lookup?id=389801252",
                AppURLUtils.appstore("389801252", "", ""));
        Assertions.assertEquals("https://itunes.apple.com/lookup?id=389801252&lang=bg",
                AppURLUtils.appstore("389801252", "", "bg"));
        Assertions.assertEquals("https://itunes.apple.com/lookup?id=389801252&country=bg",
                AppURLUtils.appstore("389801252", "bg", ""));
        Assertions.assertEquals("https://itunes.apple.com/lookup?id=389801252&country=bg&lang=bg",
                AppURLUtils.appstore("389801252", "bg", "bg"));
    }
}
