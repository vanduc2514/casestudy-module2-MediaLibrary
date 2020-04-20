package main.java.test;

import main.java.model.Song;
import main.java.model.Manager;
import main.java.service.ManagerService;
import org.junit.jupiter.api.Test;

/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/20/2020
 */class ManagerServiceTest {
    @Test
    void updateSong() {
        Song song = new Song();
        song.setTitle("hihi");
        Manager.getInstance().getSongList().add(song);
        ManagerService.getInstance().updateSong(song,1,"Nam","Nam","Nam","Nam");
        System.out.println(Manager.getInstance().getSongList().get(0));
    }
}