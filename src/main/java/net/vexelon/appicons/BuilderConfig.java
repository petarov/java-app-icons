package net.vexelon.appicons;

import java.util.concurrent.ExecutorService;

public class BuilderConfig {

    public enum ProxyType {
        NONE,
        HTTP
    }

    private long timeout = -1;
    private ExecutorService executorService;
    private String country;
    private String language;
    private ProxyType proxyType = ProxyType.NONE;
    private String proxyHost = "";
    private int proxyPort = -1;
    private String proxyUser = "";
    private String proxyPassword = "";
    private boolean isSkipSSLVerify = false;

    public long getTimeout() {
        return timeout;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
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
}
