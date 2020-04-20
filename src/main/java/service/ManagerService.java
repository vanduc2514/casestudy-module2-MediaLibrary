package main.java.service;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/20/2020
 */

import main.java.model.Song;
import main.java.model.Manager;

public class ManagerService {
    private static ManagerService managerService;

    private ManagerService() {
    }

    public static ManagerService getInstance() {
        if (managerService == null) {
            managerService = new ManagerService();
        }
        return managerService;
    }

    public Song createSong(String title, String artist, String album, String genre, int year) {
        return new Song(title, artist, album, genre,year);
    }

    public void addToDB(Song song) {
        Manager.getInstance().getSongList().add(song);
    }

    public void deleteFromDB(Song song) {
        Manager.getInstance().getSongList().remove(song);
    }

    public void updateSong(Song song, int track, String title, String artist, String album, String genre) {
        song.setTrackNumber(track);
        song.setTitle(title);
        song.setArtist(artist);
        song.setAlbum(album);
        song.setGenre(genre);
    }

}
