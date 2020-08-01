package net.vexelon.appicons.wireframe;

import net.vexelon.appicons.entities.FileIcon;
import net.vexelon.appicons.entities.UrlIcon;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface Downloader {

    /**
     * Fetches icon urls for a single {@code appId} in a blocking way.
     */
    List<UrlIcon> getUrls(String appId);

    /**
     * Fetches icon files for a single {@code appId} in a blocking way.
     */
    List<FileIcon> getFiles(String appId, Path destination);

    /**
     * Fetches icon urls for multiple app identifiers {@code appIds} in a blocking way.
     */
    Map<String, List<UrlIcon>> getMultiUrls(Set<String> appIds);

    /**
     * Fetches icon files for multiple app identifiers {@code appIds} in a blocking way.
     */
    Map<String, List<FileIcon>> getMultiFiles(Set<String> appIds, Path destination);

    /**
     * Fetches icon urls for a single {@code appId} in an asynchronous way.
     * <p>
     * The {@code callback} will be invoked when the operation is completed.
     */
    void getUrls(String appId, CompletableFuture<DownloadCallback<List<UrlIcon>>> callback);

    /**
     * Fetches icon files for a single {@code appId} in an asynchronous way.
     * <p>
     * The {@code callback} will be invoked when the operation is completed.
     */
    void getFiles(String appId, Path destination, CompletableFuture<DownloadCallback<List<FileIcon>>> callback);

    /**
     * Fetches icon urls for multiple app identifiers {@code appIds} in an asynchronous way.
     * <p>
     * The {@code callback} will be invoked when the operation is completed.
     */
    void getMultiUrls(String appId, CompletableFuture<DownloadCallback<Map<String, List<UrlIcon>>>> callback);

    /**
     * Fetches icon files for multiple app identifiers {@code appIds} in an asynchronous way.
     * <p>
     * The {@code callback} will be invoked when the operation is completed.
     */
    void getMultiFiles(String appId, Path destination,
                       CompletableFuture<DownloadCallback<Map<String, List<FileIcon>>>> callback);

}
