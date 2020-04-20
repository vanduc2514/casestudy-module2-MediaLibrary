package main.java.service;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/19/2020
 */

import main.java.model.Song;
import main.java.model.Manager;
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
import java.util.LinkedList;

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
        readMetadata(file);
        String title = metadata.get("title");
        String artist = metadata.get("xmpDM:artist");
        String album = metadata.get("xmpDM:album");
        String genre = metadata.get("xmpDM:genre");
        String creator = metadata.get("xmpDM:creator");
        int trackNumber = Integer.parseInt(metadata.get("xmpDM:trackNumber").substring(0, 2));
        int sampleRate = Integer.parseInt(metadata.get("xmpDM:audioSampleRate"));
        int year = Integer.parseInt(metadata.get("xmpDM:releaseDate"));
        Double seconds = Double.parseDouble(metadata.get("xmpDM:duration"));
        int bitrate = toBitrate(Math.floor(file.length() / seconds) * 8);
        Duration duration = Duration.ofMillis(seconds.longValue());
        Song song = ManagerService.getInstance().createSong(title, artist, album, genre, year);
        song.setDuration(duration);
        song.setCreator(creator);
        song.setTrackNumber(trackNumber);
        song.setSampleRate(sampleRate);
        song.setBitrate(bitrate);
        return song;
    }

    public void saveDB(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(Manager.getInstance().getSongList());
        objectOutputStream.close();
    }

    public LinkedList<Song> readDB(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return (LinkedList<Song>) objectInputStream.readObject();
    }

    private void readMetadata(File file) throws IOException, TikaException, SAXException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ContentHandler handler = new DefaultHandler();
        metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseContext = new ParseContext();
        parser.parse(fileInputStream, handler, metadata, parseContext);
        fileInputStream.close();
    }

    private int toBitrate(double averageBitrate) {
        if (32 < averageBitrate && averageBitrate < 96) {
            return 32;
        } else if (averageBitrate < 128) {
            return 96;
        } else if (averageBitrate < 192) {
            return 128;
        } else if (averageBitrate < 256) {
            return 192;
        } else if (averageBitrate < 320) {
            return 256;
        } else return 320;
    }
}
