package main.java.service.file;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/21/2020
 */

import main.java.service.song.SongManager;

import java.io.File;
import java.util.HashMap;

//Strategy Pattern
public interface FileService {
    void importSong(File file, SongManager songManager);

    void saveList(File file, SongManager songManager);

    SongManager readList(File file);

    void setMedata(File file, HashMap<String, String> propertyMap);

}
