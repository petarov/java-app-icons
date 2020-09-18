package net.vexelon.appicons.wireframe;

import net.vexelon.appicons.wireframe.entities.IconFile;
import net.vexelon.appicons.wireframe.entities.IconURL;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public interface AsyncDownloader {

    /**
     * Fetches icon urls for a single {@code appId} in an asynchronous way.
     * <p>
     * The {@code callback} will be invoked when the operation is completed.
     */
    void getUrls(String appId, DownloadCallback<List<IconURL>> callback);

    /**
     * Fetches icon files for a single {@code appId} in an asynchronous way.
     * <p>
     * The {@code callback} will be invoked when the operation is completed.
     */
    void getFiles(String appId, Path destination, DownloadCallback<IconFile> callback);

    /**
     * Fetches icon urls for multiple app identifiers {@code appIds} in an asynchronous way.
     * <p>
     * The {@code callback} will be invoked when the operation is completed.
     */
    void getMultiUrls(Set<String> appIds, DownloadCallback<List<IconURL>> callback);

    /**
     * Fetches icon files for multiple app identifiers {@code appIds} in an asynchronous way.
     * <p>
     * The {@code callback} will be invoked when the operation is completed.
     */
    void getMultiFiles(Set<String> appIds, Path destination,
                       DownloadCallback<IconFile> callback);
}
