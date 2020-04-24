package main.java.service.file;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/21/2020
 */

import main.java.service.manager.SongDaoManager;

import java.io.File;
import java.util.HashMap;

//Strategy Pattern
public interface FileService {
    void importSong(File file, SongDaoManager songManager);

    void saveList(File file, SongDaoManager songManager);

    SongDaoManager readList(File file);

    void setMedata(File file, HashMap<String, String> propertyMap);

}
