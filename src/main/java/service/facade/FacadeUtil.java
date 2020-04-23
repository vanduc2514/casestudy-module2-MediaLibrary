package main.java.service.facade;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/23/2020
 */

import javafx.scene.image.Image;
import main.java.model.Song;
import java.io.File;
import java.util.AbstractMap;
import java.util.List;

//Facade Pattern + Strategy Pattern
public interface FacadeUtil {
    List<Song> createNewList();

    List<Song> loadDisplayList(String path);

    List<Song> getDisplayList();

    void saveLibrary(String path);

    void importFiles(List<File> files);

    Song getLastImport();

    void removeSong(Song song);

    void deleteSong(Song song);

    void editInfo(Song song, AbstractMap<String, String> propertyMap);

    File getSongFolder(Song song);

    Image getAlbumArt(Song song);
}
