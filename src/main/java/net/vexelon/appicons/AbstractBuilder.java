package net.vexelon.appicons;

import net.vexelon.appicons.wireframe.Downloader;

public abstract class AbstractBuilder<BUILDER extends AbstractBuilder<BUILDER>> {

    @SuppressWarnings("unchecked") final BUILDER self() {
        return (BUILDER) this;
    }

    public BUILDER timeout(long timeout) {
        config().setTimeout(timeout);
        return self();
    }

    public BUILDER setCountry(String country) {
        config().setCountry(country);
        return self();
    }

    public BUILDER setLanguage(String language) {
        config().setLanguage(language);
        return self();
    }

    public abstract BuilderConfig config();

    public abstract Downloader build();
}
