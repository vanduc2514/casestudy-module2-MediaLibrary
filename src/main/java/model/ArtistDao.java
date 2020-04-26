package model;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/24/2020
 */

import java.io.Serializable;
import java.util.ArrayList;

public class ArtistDao implements Serializable, SongData {
    private String title;
    private ArrayList<SongDao> songDaoList;

    public ArtistDao() {
        songDaoList = new ArrayList<>();
    }

    public ArtistDao(String title) {
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
        return this.title.equals(((ArtistDao) obj).getTitle());
    }

    @Override
    public int compareTo(SongData anotherArtistDao) {
        return title.compareTo(anotherArtistDao.getTitle());
    }

    @Override
    public String toString() {
        return title;
    }
}
