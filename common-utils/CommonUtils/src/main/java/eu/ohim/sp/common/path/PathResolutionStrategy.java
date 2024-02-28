package eu.ohim.sp.common.path;

/**
 * Resolved file's path. Used to load file from file system
 *
 * @author Maciej Walkowiak
 */
public interface PathResolutionStrategy {
    String resolvePath(String path);
}
