package main.java.service;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/21/2020
 */

import main.java.model.Song;

import java.util.List;

public interface SongManager {

    void addToList(Song song);

    void removeFromList(Song song);

    List<Song> getSongList();

    void setSongList(List<Song> list);
}
