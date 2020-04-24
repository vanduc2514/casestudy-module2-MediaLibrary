package main.java.service.facade;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/23/2020
 */

import javafx.scene.image.Image;
import main.java.model.*;
import main.java.service.dao.SongDaoManager;
import main.java.service.dao.SongDaoManagerImp;
import main.java.service.file.FileService;
import main.java.service.file.FileUtil;
import main.java.service.file.Mp3MagicService;
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
    private static SongDaoManager songManager;
    private File file;
    private static LibraryUtil manager;

    private LibraryUtil() {
    }

    public static LibraryUtil getInstance() {
        if (manager == null) {
            manager = new LibraryUtil();
            songManager = new SongDaoManagerImp();
            fileService = new Mp3MagicService();
        }
        return manager;
    }

    public List<SongData> getArtistDaoList() {
        Mp3MagicService service = (Mp3MagicService) fileService;
        return service.getArtistDaoList();
    }

    public List<SongData> getAlbumDaoList() {
        Mp3MagicService service = (Mp3MagicService) fileService;
        return service.getAlbumDaoList();

    }

    public List<SongData> getGenreDaoList() {
        Mp3MagicService service = (Mp3MagicService) fileService;
        return service.getGenreDaoList();
    }

    @Override
    public List<SongDao> createNewList() {
        songManager = new SongDaoManagerImp();
        return songManager.getSongDaoList();
    }

    @Override
    public List<SongDao> loadDisplayList(String path) {
        file = new File(path);
        songManager = fileService.readList(file);
        return songManager.getSongDaoList();
    }

    @Override
    public List<SongDao> getDisplayList() {
        return songManager.getSongDaoList();
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
    public SongDao getLastImport() {
        return songManager.getLastAdd();
    }

    @Override
    public void removeSong(SongDao song) {
        songManager.removeSongDao(song);
    }

    @Override
    public void deleteSong(SongDao song) {
        file = new File(song.getPath());
        if (!file.delete()) {
            throw new UnsupportedOperationException("Error delete File!");
        }
        removeSong(song);
    }

    @Override
    public void editInfo(SongDao song, AbstractMap<String, String> propertyMap) {
        file = new File(song.getPath());
        HashMap<String, String> mapConverted = (HashMap<String, String>) propertyMap;
        fileService.setMedata(file, mapConverted);
        updateSongInfo(song, mapConverted);
    }

    @Override
    public File getSongFolder(SongDao song) {
        file = new File(song.getPath());
        return file.getParentFile();
    }

    private void updateSongInfo(SongDao song, HashMap<String, String> propertyMap) {
        song.setTrackNumber(propertyMap.get("track"));
        song.setTitle(propertyMap.get("title"));
        song.getArtistDao().setTitle(propertyMap.get("artist"));
        song.getAlbumDao().setTitle(propertyMap.get("album"));
        song.getGenreDao().setTitle(propertyMap.get("genre"));
        song.setComposer(propertyMap.get("composer"));
        song.setYear(Integer.parseInt(propertyMap.get("year")));
        if (propertyMap.get("albumArtPath") != null) {
            String path = propertyMap.get("albumArtPath");
            byte[] albumArt = new byte[0];
            try {
                albumArt = FileUtil.getInstance().getAlbumArt(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            song.setAlbumArt(albumArt);
        }
    }

    @Override
    public Image getAlbumArt(SongDao song) {
        InputStream is = new ByteArrayInputStream(song.getAlbumArt());
        return new Image(is);
    }
}
