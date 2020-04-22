package main.java.service.facade;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/23/2020
 */

import main.java.model.Song;

import java.io.File;
import java.util.AbstractMap;
import java.util.List;

//Facade Pattern + Strategy Pattern
public interface ManagerFacade {
    List<Song> createNewList();

    List<Song> loadDisplayList(String path);

    List<Song> getDisplayList();

    void saveLibrary(String path);

    void importFile(List<File> files);

    Song getLastImport();

    void removeSong(Song song);

    void deleteSong(Song song);

    Song editInfo(Song song, AbstractMap<String, String> propertyMap);

    File getSongFolder(Song song);
}
