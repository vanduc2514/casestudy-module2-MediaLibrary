package main.java.service;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/20/2020
 */

import main.java.model.Song;
import java.util.LinkedList;
import java.util.List;

public class LinkedListManager implements SongManager {
    private List<Song> songList;
    private static LinkedListManager manager;

    private LinkedListManager(){}

    public static LinkedListManager getInstance() {
        if (manager == null) {
            manager = new LinkedListManager();
            manager.songList = new LinkedList<>();
        }
        return manager;
    }

    @Override
    public void removeFromList(Song song) {
        songList.remove(song);
    }

    @Override
    public void addToList(Song song) {
        songList.add(song);
    }

    @Override
    public List<Song> getSongList() {
        return songList;
    }

    @Override
    public void setSongList(List<Song> list) {
        this.songList = list;
    }
}
