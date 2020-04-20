package main.java.model;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/20/2020
 */

import java.io.Serializable;
import java.util.LinkedList;

public class Manager implements Serializable {
    private static Manager manager;
    private static LinkedList<Song> songList;

    private Manager(){}

    public static Manager getInstance() {
        if (manager == null || songList == null) {
            manager = new Manager();
            songList = new LinkedList<>();
        }
        return manager;
    }

    public void setSongList(LinkedList<Song> songList) {
        Manager.songList = songList;
    }

    public LinkedList<Song> getSongList() {
        return songList;
    }
}
