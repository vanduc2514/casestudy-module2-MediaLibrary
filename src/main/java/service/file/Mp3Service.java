package main.java.service.file;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/19/2020
 */

import main.java.model.SongDao;
import main.java.service.manager.SongDaoManager;
import main.java.service.manager.SongDaoManagerImp;
import java.io.*;
import java.util.HashMap;

//Template Pattern
public abstract class Mp3Service implements FileService {
    @Override
    public final void importSong(File file, SongDaoManager songManager) {
        SongDao song = parseSong(file);
        if (!songManager.getSongDaoList().contains(song)) {
            songManager.addSongDao(song);
        }
    }

    @Override
    public final void saveList(File file, SongDaoManager songManager) {
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
    public final SongDaoManager readList(File file) {
        InputStream inputStream;
        SongDaoManager songManager;
        try {
            inputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            songManager = (SongDaoManager) objectInputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            songManager = new SongDaoManagerImp();
        }
        return songManager;
    }

    public abstract void setMedata(File file, HashMap<String, String> propertyMap);

    public abstract SongDao parseSong(File file);
}
