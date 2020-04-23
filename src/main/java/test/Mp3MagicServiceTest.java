package main.java.test;

import com.mpatric.mp3agic.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/23/2020
 */class Mp3MagicServiceTest {

    @Test
    void parseSong() throws InvalidDataException, IOException, UnsupportedTagException {
        Mp3File mp3File = new Mp3File("F:/Users/Duc/Downloads/Khoi Dau - Hoang Thuy Linh (1).mp3");
        ID3v2 id3v2tag = mp3File.getId3v2Tag();
        CharSequence text;
        Duration duration = Duration.ofMillis(mp3File.getLength());
        System.out.println(duration);
    }
}