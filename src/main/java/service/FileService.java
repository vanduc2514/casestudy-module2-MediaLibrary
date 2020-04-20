package main.java.service;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/19/2020
 */

import main.java.model.Song;
import main.java.model.SongManger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.time.*;

public class FileService {
    private Metadata metadata;
    private static FileService instance;

    private FileService() {
    }

    public static FileService getInstance() {
        if (instance == null) {
            instance = new FileService();
        }
        return instance;
    }

    public Song importSong(File file) throws TikaException, IOException, SAXException {
        readSong(file);
        String title = metadata.get("title");
        String artist = metadata.get("xmpDM:artist");
        String album = metadata.get("xmpDM:album");
        String genre = metadata.get("xmpDM:genre");
        String creator = metadata.get("xmpDM:creator");
        int trackNumber = Integer.parseInt(metadata.get("xmpDM:trackNumber").substring(0,2));
        int sampleRate = Integer.parseInt(metadata.get("xmpDM:audioSampleRate"));
        Double durationDouble = Double.parseDouble(metadata.get("xmpDM:duration"));
        Duration duration = Duration.ofMillis(durationDouble.longValue());
        Song song = SongService.getInstance().createSong(title, artist, album, genre);
        song.setDuration(duration);
        song.setCreator(creator);
        song.setTrackNumber(trackNumber);
        song.setSampleRate(sampleRate);
        return song;
    }

    public void saveDB(SongManger songManger, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(songManger);
        objectOutputStream.close();
    }

    public SongManger readDB(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return (SongManger) objectInputStream.readObject();
    }

    private void readSong(File file) throws IOException, TikaException, SAXException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ContentHandler handler = new DefaultHandler();
        metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseContext = new ParseContext();
        parser.parse(fileInputStream, handler, metadata, parseContext);
        fileInputStream.close();
    }
}
