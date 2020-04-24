package main.java.service.facade;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/23/2020
 */

import javafx.scene.image.Image;
import main.java.model.Song;
import main.java.model.SongDao;

import java.io.File;
import java.util.AbstractMap;
import java.util.List;

//Facade Pattern + Strategy Pattern
public interface FacadeUtil {
    List<SongDao> createNewList();

    List<SongDao> loadDisplayList(String path);

    List<SongDao> getDisplayList();

    void saveLibrary(String path);

    void importFiles(List<File> files);

    SongDao getLastImport();

    void removeSong(SongDao song);

    void deleteSong(SongDao song);

    void editInfo(SongDao song, AbstractMap<String, String> propertyMap);

    File getSongFolder(SongDao song);

    Image getAlbumArt(SongDao song);
}
