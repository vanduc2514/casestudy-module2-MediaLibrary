package main.java.service;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/21/2020
 */

import libs.mp3agic.InvalidDataException;
import libs.mp3agic.UnsupportedTagException;
import main.java.model.Song;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    void importSong(File file, SongManager songManager) throws InvalidDataException, IOException, UnsupportedTagException;

    void saveList(File file, SongManager songManager) throws IOException;

    void readList(File file, SongManager songManager) throws IOException, ClassNotFoundException;

}
