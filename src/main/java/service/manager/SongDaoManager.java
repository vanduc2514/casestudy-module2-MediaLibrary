package service.manager;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/24/2020
 */

import model.SongDao;

import java.io.Serializable;
import java.util.List;

public interface SongDaoManager extends Serializable {
    void addSongDao(SongDao SongDao);

    void removeSongDao(SongDao SongDao);

    List<SongDao> getSongDaoList();

    SongDao getLastAdd();


}
