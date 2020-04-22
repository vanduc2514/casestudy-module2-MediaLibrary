package main.java.service.facade;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/23/2020
 */

import main.java.model.Song;
import main.java.service.file.FileService;
import main.java.service.file.Mp3MagicService;
import main.java.service.song.LinkedListManager;
import main.java.service.song.SongManager;
import java.io.File;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

//Singleton Pattern + Facade Pattern
public class MyManager implements ManagerFacade {
    private static FileService fileService;
    private static SongManager songManager;
    private File file;
    private static MyManager manager;

    private MyManager() {
    }

    public static MyManager getInstance() {
        if (manager == null) {
            manager = new MyManager();
            songManager = new LinkedListManager();
            fileService = new Mp3MagicService();
        }
        return manager;
    }

    @Override
    public List<Song> createNewList() {
        songManager = new LinkedListManager();
        return songManager.getSongList();
    }

    @Override
    public List<Song> loadDisplayList(String path) {
        file = new File(path);
        songManager = fileService.readList(file);
        return songManager.getSongList();
    }

    @Override
    public List<Song> getDisplayList() {
        return songManager.getSongList();
    }

    @Override
    public void saveLibrary(String path) {
        file = new File(path);
        fileService.saveList(file, songManager);
    }

    @Override
    public void importFile(List<File> files) {
        for (File file : files) {
            fileService.importSong(file, songManager);
        }
    }

    @Override
    public Song getLastImport() {
        return songManager.getLastAdd();
    }

    @Override
    public void removeSong(Song song) {
        songManager.removeSong(song);
    }

    @Override
    public void deleteSong(Song song) {
        file = new File(song.getPath());
        if (!file.delete()) {
            throw new UnsupportedOperationException("Error delete File!");
        }
    }

    @Override
    public Song editInfo(Song song, AbstractMap<String, String> propertyMap) {
        file = new File(song.getPath());
        HashMap<String, String> mapConverted = (HashMap<String, String>) propertyMap;
        fileService.setMedata(file, mapConverted);
        fileService.importSong(file, songManager);
        return songManager.getLastAdd();
    }

    @Override
    public File getSongFolder(Song song) {
        file = new File(song.getPath());
        return file.getParentFile();
    }
}
