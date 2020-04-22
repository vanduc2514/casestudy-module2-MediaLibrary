package main.java.service.song;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/20/2020
 */

import main.java.model.Song;

import java.util.LinkedList;
import java.util.List;

//Concrete Class
public class LinkedListManager implements SongManager {
    private LinkedList<Song> songList;

    public LinkedListManager() {
        songList = new LinkedList<>();
    }

    @Override
    public void addSong(Song song) {
        songList.add(song);
    }

    @Override
    public void removeSong(Song song) {
        songList.remove(song);
    }

    @Override
    public List<Song> getSongList() {
        return songList;
    }

    @Override
    public Song getLastAdd() {
        return songList.get(songList.size() - 1);
    }
}
