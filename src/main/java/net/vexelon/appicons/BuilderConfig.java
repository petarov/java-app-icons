package net.vexelon.appicons;

import net.vexelon.appicons.utils.HashingUtils;
import net.vexelon.appicons.wireframe.NamingStrategyResolver;
import net.vexelon.appicons.wireframe.entities.IconURL;

import java.util.concurrent.ExecutorService;

public class BuilderConfig {

    public static final String DEFAULT_USER_AGENT = "java-app-icons/1.2";

    public enum ProxyType {
        /**
         * No proxy
         */
        NONE,
        /**
         * HTTP/S proxy selection
         */
        HTTP
    }

    public enum NamingStrategy implements NamingStrategyResolver {
        /**
         * Icon file name is a SHA-1 values of the icon url.
         */
        SHA1 {
            @Override
            public String resolve(String appId, IconURL iconURL) {
                return HashingUtils.sha1(iconURL.getUrl()) + "." + iconURL.getType().toLowerCase();
            }
        },
        /**
         * Icon file name is composed of the app/bundle id plus the size dimension of the icon.
         */
        APPID_AND_SIZE {
            @Override
            public String resolve(String appId, IconURL iconURL) {
                return appId + "-" + iconURL.getWidth() + "x." + iconURL.getType().toLowerCase();
            }
        }
    }

    private long timeout = -1;
    private String userAgent = DEFAULT_USER_AGENT;
    private ExecutorService executorService;
    private String country;
    private String language;
    private ProxyType proxyType = ProxyType.NONE;
    private String proxyHost = "";
    private int proxyPort = -1;
    private String proxyUser = "";
    private String proxyPassword = "";
    private boolean isSkipSSLVerify = false;
    private NamingStrategyResolver namingStrategyResolver = NamingStrategy.SHA1;

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
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

    public ProxyType getProxyType() {
        return proxyType;
    }

    public void setProxyType(ProxyType proxyType) {
        this.proxyType = proxyType;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUser() {
        return proxyUser;
    }

    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public boolean isSkipSSLVerify() {
        return isSkipSSLVerify;
    }

    public void setSkipSSLVerify(boolean skipSSLVerify) {
        isSkipSSLVerify = skipSSLVerify;
    }

    public NamingStrategyResolver getNamingStrategyResolver() {
        return namingStrategyResolver;
    }

    public void setNamingStrategyResolver(NamingStrategyResolver namingStrategyResolver) {
        this.namingStrategyResolver = namingStrategyResolver;
    }
}
