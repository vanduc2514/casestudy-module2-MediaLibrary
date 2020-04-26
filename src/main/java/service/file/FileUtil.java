package service.file;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/23/2020
 */

import java.io.*;

//Adapter Pattern + Singleton Pattern
public class FileUtil {
    private static FileUtil fileUtil;

    private FileUtil() {
    }

    public static FileUtil getInstance() {
        if (fileUtil == null) {
            fileUtil = new FileUtil();
        }
        return fileUtil;
    }

    public byte[] getAlbumArt(String path) throws IOException {
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        byte[] albumArt = new byte[(int) file.length()];
        if (file.length() > 0) {
            if (inputStream.read(albumArt) != -1) {
                return albumArt;
            }
        }
        return null;
    }
}
