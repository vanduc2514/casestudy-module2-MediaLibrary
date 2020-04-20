package main.java.model;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/20/2020
 */

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class SongManger implements Serializable {
    private List<Song> songList;

    public SongManger() {
        songList = new LinkedList<>();
    }

    public List<Song> getSongList() {
        return songList;
    }
}
