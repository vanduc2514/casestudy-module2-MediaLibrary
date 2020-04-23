package main.java.test;

import com.mpatric.mp3agic.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/23/2020
 */class Mp3MagicServiceTest {

    @Test
    void parseSong() throws InvalidDataException, IOException, UnsupportedTagException {
        Mp3File mp3File = new Mp3File("F:/Users/Duc/Downloads/Khoi Dau - Hoang Thuy Linh (1).mp3");
        ID3v1 id3v1tag = mp3File.getId3v1Tag();
        System.out.println(mp3File.hasId3v2Tag());
    }
}