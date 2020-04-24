package main.java.service.dao;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/24/2020
 */

import main.java.model.SongDao;

import java.util.ArrayList;
import java.util.List;

public class SongDaoManagerImp implements SongDaoManager {
    private List<SongDao> SongDaoList;

    public SongDaoManagerImp() {
        SongDaoList = new ArrayList<>();
    }

    public void addSongDao(SongDao SongDao) {
        SongDaoList.add(SongDao);
    }

    public void removeSongDao(SongDao SongDao) {
        SongDaoList.remove(SongDao);
    }

    public List<SongDao> getSongDaoList() {
        return SongDaoList;
    }

    public SongDao getLastAdd() {
        return SongDaoList.get(SongDaoList.size() - 1);
    }

}
