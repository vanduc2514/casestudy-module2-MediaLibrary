package main.java.service.facade;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/23/2020
 */

import javafx.scene.image.Image;
import main.java.model.Song;
import main.java.service.file.FileService;
import main.java.service.file.FileServiceAdapter;
import main.java.service.file.Mp3MagicService;
import main.java.service.song.ArrayListManager;
import main.java.service.song.SongManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

//Singleton Pattern + Facade Pattern + Adapter Pattern
public class LibraryUtil implements FacadeUtil {
    private static FileService fileService;
    private static SongManager songManager;
    private File file;
    private static LibraryUtil manager;

    private LibraryUtil() {
    }

    public static LibraryUtil getInstance() {
        if (manager == null) {
            manager = new LibraryUtil();
            songManager = new ArrayListManager();
            fileService = new Mp3MagicService();
        }
        return manager;
    }

    @Override
    public List<Song> createNewList() {
        songManager = new ArrayListManager();
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
    public void importFiles(List<File> files) {
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
    public void editInfo(Song song, AbstractMap<String, String> propertyMap) {
        file = new File(song.getPath());
        HashMap<String, String> mapConverted = (HashMap<String, String>) propertyMap;
        fileService.setMedata(file, mapConverted);
        updateSongInfo(song, mapConverted);
    }

    @Override
    public File getSongFolder(Song song) {
        file = new File(song.getPath());
        return file.getParentFile();
    }

    private void updateSongInfo(Song song, HashMap<String, String> propertyMap) {
        song.setTrackNumber(propertyMap.get("track"));
        song.setTitle(propertyMap.get("title"));
        song.setArtist(propertyMap.get("artist"));
        song.setAlbum(propertyMap.get("album"));
        song.setGenre(propertyMap.get("genre"));
        song.setComposer(propertyMap.get("composer"));
        song.setYear(Integer.parseInt(propertyMap.get("year")));
        if (propertyMap.get("albumArtPath") != null) {
            String path = propertyMap.get("albumArtPath");
            FileServiceAdapter adapter = new FileServiceAdapter(fileService);
            byte[] albumArt = new byte[0];
            try {
                albumArt = adapter.getAlbumArt(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            song.setAlbumArt(albumArt);
        }
    }

    @Override
    public Image getAlbumArt(Song song) {
        InputStream is = new ByteArrayInputStream(song.getAlbumArt());
        return new Image(is);
    }
}
