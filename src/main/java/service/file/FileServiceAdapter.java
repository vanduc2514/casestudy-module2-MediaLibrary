package main.java.service.file;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/23/2020
 */

import main.java.model.Song;
import main.java.service.song.SongManager;

import java.io.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

//Adapter Pattern
public class FileServiceAdapter {
    private FileService fileService;
    private File file;
    private SongManager songManager;

    public FileServiceAdapter(FileService fileService) {
        this.fileService = fileService;
    }

    public SongManager getSongManager() {
        return songManager;
    }

    public void importSong(String path, List<Song> list) {
        file = new File(path);
        songManager = new SongManager() {
            @Override
            public void addSong(Song song) {
                list.add(song);
            }

            @Override
            public void removeSong(Song song) {
                list.remove(song);
            }

            @Override
            public List<Song> getSongList() {
                return list;
            }

            @Override
            public Song getLastAdd() {
                return list.get(list.size() - 1);
            }
        };
        fileService.importSong(file, songManager);
    }

    public void saveList(String path, List<Song> list) {
        file = new File(path);
        songManager = new SongManager() {
            @Override
            public void addSong(Song song) {
                list.add(song);
            }

            @Override
            public void removeSong(Song song) {
                list.remove(song);
            }

            @Override
            public List<Song> getSongList() {
                return list;
            }

            @Override
            public Song getLastAdd() {
                return list.get(list.size() - 1);
            }
        };
        fileService.saveList(file, songManager);
    }

    public List<Song> readList(String path) {
        file = new File(path);
        return fileService.readList(file).getSongList();
    }

    public void setMedata(String path, AbstractMap<String, String> propertyMap) {
        file = new File(path);
        HashMap<String, String> hashMap = (HashMap<String, String>) propertyMap;
        fileService.setMedata(file, hashMap);
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
