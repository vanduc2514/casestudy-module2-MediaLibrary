package main.java.service;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/20/2020
 */

import main.java.model.Song;
import main.java.model.SongManger;

public class SongService {
    private static SongService songService;

    private SongService() {
    }

    public static SongService getInstance() {
        if (songService == null) {
            songService = new SongService();
        }
        return songService;
    }

    public Song createSong(String title, String artist, String album, String genre) {
        return new Song(title,artist,album,genre);
    }

    public void addToDB(Song song, SongManger db) {
        db.getSongList().add(song);
    }

    public void deleteFromDB(Song song, SongManger db) {
        db.getSongList().remove(song);
    }

    public void editInfo(Song song, String title, String artist, String album, String genre, String creator) {
        song.setTitle(title);
        song.setArtist(artist);
        song.setAlbum(album);
        song.setGenre(genre);
        song.setCreator(creator);
    }
}
