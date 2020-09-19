package net.vexelon.appicons.wireframe;

import net.vexelon.appicons.wireframe.entities.IconFile;
import net.vexelon.appicons.wireframe.entities.IconURL;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Blocking API
 */
public interface BioDownloader {
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
}
