package service.manager;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/24/2020
 */

import model.SongDao;
import java.util.ArrayList;
import java.util.List;

public class SongDaoManagerImp implements SongDaoManager {
    private List<SongDao> songDaoList;

    public SongDaoManagerImp() {
        songDaoList = new ArrayList<>();
    }

    public void addSongDao(SongDao SongDao) {
        songDaoList.add(SongDao);
    }

    public void removeSongDao(SongDao SongDao) {
        songDaoList.remove(SongDao);
    }

    public List<SongDao> getSongDaoList() {
        return songDaoList;
    }

    public SongDao getLastAdd() {
        return songDaoList.get(songDaoList.size() - 1);
    }
}
