package model;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/24/2020
 */

import java.io.Serializable;
import java.util.ArrayList;

public class AlbumDao implements Serializable, SongData {
    private String title;
    private ArrayList<SongDao> songDaoList;

    public AlbumDao() {
        songDaoList = new ArrayList<>();
    }

    public AlbumDao(String title) {
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
        return this.title.equals(((AlbumDao) obj).getTitle());
    }

    @Override
    public int compareTo(SongData anotherAlbumDao) {
        return title.compareTo(anotherAlbumDao.getTitle());
    }

    @Override
    public String toString() {
        return title;
    }
}
