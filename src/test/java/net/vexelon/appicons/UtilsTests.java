package net.vexelon.appicons;

import net.vexelon.appicons.utils.AppURLUtils;
import net.vexelon.appicons.utils.HashingUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTests {

    @Test
    void testHashSums() {
        Assertions.assertEquals("071c823fa36d17a5a51a31f9937b25cf2b9e9758",
                HashingUtils.sha1("https://is4-ssl.mzstatic.com/image/thumb/Purple124/v4/ae/5e/26/ae5e268c-44d3-471c-f751-546b7db58980/source/512x512bb.jpg"));
        Assertions.assertEquals("d8676f92e49e73ee6c8ebdbc72e96234faf13271",
                HashingUtils.sha1("https://lh3.googleusercontent.com/2sREY-8UpjmaLDCTztldQf6u2RGUtuyf6VT5iyX3z53JS4TdvfQlX-rNChXKgpBYMw=s360-rw"));
        Assertions.assertEquals("45563c8d6cbc811007b137bfbefbee5a9d30d2ea",
                Utils.sha1Res("3c26c13a6511eef9b2b7f6e0dcb79e39f295e40d.png"));
    }

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
