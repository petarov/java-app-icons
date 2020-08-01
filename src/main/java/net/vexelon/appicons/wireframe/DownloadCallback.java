package net.vexelon.appicons.wireframe;

public interface DownloadCallback<RESULT> {

    void onError(Throwable t);

    void onSuccess(RESULT result);
}
