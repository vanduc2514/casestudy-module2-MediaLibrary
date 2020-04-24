package main.java.model;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/24/2020
 */

import java.io.Serializable;
import java.util.ArrayList;

public class GenreDao implements Serializable, SongData {
    private String title;
    private ArrayList<SongDao> songDaoList;

    public GenreDao() {
        songDaoList = new ArrayList<>();
    }

    public GenreDao(String title) {
        this.title = title;
        songDaoList = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<SongDao> getSongDaoList() {
        return songDaoList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        return this.title.equals(((GenreDao) obj).getTitle());
    }

    @Override
    public int compareTo(SongData anotherGenreDao) {
        return title.compareTo(anotherGenreDao.getTitle());
    }

    @Override
    public String toString() {
        return title;
    }
}
