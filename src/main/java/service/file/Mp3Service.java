package main.java.service.file;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/19/2020
 */

import main.java.model.Song;
import main.java.service.song.LinkedListManager;
import main.java.service.song.SongManager;
import java.io.*;
import java.util.HashMap;

//Template Pattern
public abstract class Mp3Service implements FileService {
    @Override
    public final void importSong(File file, SongManager songManager) {
        Song song = parseSong(file);
        if (!songManager.getSongList().contains(song)) {
            songManager.addSong(song);
        }
    }

    @Override
    public final void saveList(File file, SongManager songManager) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            OutputStream outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(songManager);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final SongManager readList(File file) {
        InputStream inputStream;
        SongManager manager;
        try {
            inputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            manager = (SongManager) objectInputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            manager = new LinkedListManager();
        }
        return manager;
    }

    public abstract void setMedata(File file, HashMap<String, String> propertyMap);

    public abstract Song parseSong(File file);
}
