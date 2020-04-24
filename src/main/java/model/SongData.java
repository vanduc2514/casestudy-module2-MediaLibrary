package main.java.model;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/25/2020
 */

import java.util.List;

public interface SongData extends Comparable<SongData> {
   String getTitle();

    void setTitle(String title);

    List<SongDao> getSongDaoList();

    int compareTo(SongData anotherData);
}
