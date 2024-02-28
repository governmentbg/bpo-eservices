package eu.ohim.sp.core.configuration.dao;

import java.io.IOException;

/**
 * Encapsulates file operations used by {@link ConfigParamDAO}
 *
 * @author Maciej Walkowiak
 */
interface FileOperations {
    /**
     * Returns content of the file given by path as String
     *
     * @param path - file path, obtained usually from database. Depending on used {@link PathResolutionStrategy} can be a full path or just suffix
     * @return - content of the file
     * @throws IOException
     */
    String loadFileContent(String path) throws IOException;

    /**
     * Saves string content in file. Overwrites file if one already exist
     *
     * @param path - file path, obtained usually from database. Depending on used {@link PathResolutionStrategy} can be a full path or just suffix
     * @param content - string content that will be saved in file
     * @throws IOException
     */
    void saveFileContent(String path, String content) throws IOException;
}
