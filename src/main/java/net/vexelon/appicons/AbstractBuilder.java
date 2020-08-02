package net.vexelon.appicons;

import net.vexelon.appicons.wireframe.Downloader;

public abstract class AbstractBuilder<BUILDER extends AbstractBuilder<BUILDER>> {

    // TODO proxy

    public class BuilderConfig {

        private long timeout = -1;
        private String country;
        private String language;

        public long getTimeout() {
            return timeout;
        }

        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }

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
