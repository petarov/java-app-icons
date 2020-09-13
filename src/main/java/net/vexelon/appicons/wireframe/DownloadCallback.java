package net.vexelon.appicons.wireframe;

public interface DownloadCallback<RESULT> {

    void onError(String appId, Throwable t);

    void onSuccess(String appId, RESULT result);
}
