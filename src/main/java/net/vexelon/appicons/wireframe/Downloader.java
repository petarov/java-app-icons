package net.vexelon.appicons.wireframe;

import net.vexelon.appicons.wireframe.entities.IconFile;
import net.vexelon.appicons.wireframe.entities.IconURL;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface Downloader {

    /**
     * Fetches icon urls for a single {@code appId} in a blocking way.
     */
    List<IconURL> getUrls(String appId);

    /**
     * Fetches icon files for a single {@code appId} in a blocking way.
     */
    List<IconFile> getFiles(String appId, Path destination);

    /**
     * Fetches icon urls for multiple app identifiers {@code appIds} in a blocking way.
     */
    Map<String, List<IconURL>> getMultiUrls(Set<String> appIds);

    /**
     * Fetches icon files for multiple app identifiers {@code appIds} in a blocking way.
     */
    Map<String, List<IconFile>> getMultiFiles(Set<String> appIds, Path destination);

    /**
     * Fetches icon urls for a single {@code appId} in an asynchronous way.
     * <p>
     * The {@code callback} will be invoked when the operation is completed.
     */
    void getUrls(String appId, CompletableFuture<DownloadCallback<List<IconURL>>> callback);

    /**
     * Fetches icon files for a single {@code appId} in an asynchronous way.
     * <p>
     * The {@code callback} will be invoked when the operation is completed.
     */
    void getFiles(String appId, Path destination, CompletableFuture<DownloadCallback<List<IconFile>>> callback);

    /**
     * Fetches icon urls for multiple app identifiers {@code appIds} in an asynchronous way.
     * <p>
     * The {@code callback} will be invoked when the operation is completed.
     */
    void getMultiUrls(String appId, CompletableFuture<DownloadCallback<Map<String, List<IconURL>>>> callback);

    /**
     * Fetches icon files for multiple app identifiers {@code appIds} in an asynchronous way.
     * <p>
     * The {@code callback} will be invoked when the operation is completed.
     */
    void getMultiFiles(String appId, Path destination,
                       CompletableFuture<DownloadCallback<Map<String, List<IconFile>>>> callback);

}
