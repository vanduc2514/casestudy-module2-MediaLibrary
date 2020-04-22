package main.java.service.song;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/21/2020
 */

import main.java.model.Song;

import java.io.Serializable;
import java.util.List;

//Strategy Pattern
public interface SongManager extends Serializable {
    void addSong(Song song);

    void removeSong(Song song);

    List<Song> getSongList();

    Song getLastAdd();
}
