package main.java.test;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/25/2020
 */


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class TestMain {

    public static void main(String[] args) {
        File file = new File("F:\\Users\\Duc\\Downloads\\Khoi Dau - Hoang Thuy Linh.mp3");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
