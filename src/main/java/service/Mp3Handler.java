package main.java.service;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/19/2020
 */

import libs.mp3agic.*;
import main.java.model.Song;

import java.io.*;
import java.time.Duration;
import java.util.List;

public class Mp3Handler implements FileService {
    @Override
    public void importSong(File file, SongManager songManager) throws InvalidDataException, IOException, UnsupportedTagException {
        Song song = parseSong(file);
        if (!songManager.getSongList().contains(song)) {
            songManager.addToList(song);
        }
    }

    @Override
    public void saveList(File file, SongManager songManager) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(songManager.getSongList());
        objectOutputStream.close();
    }

    @Override
    public void readList(File file, SongManager songManager) throws IOException, ClassNotFoundException {
        InputStream inputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        List<Song> songList = (List<Song>) objectInputStream.readObject();
        objectInputStream.close();
        songManager.setSongList(songList);
    }

    public Song parseSong(File file) throws InvalidDataException, IOException, UnsupportedTagException {
        Mp3File mp3File = new Mp3File(file);
        ID3v2 id3v2Tag = mp3File.getId3v2Tag();
        Song song = new Song();
        song.setTrackNumber(Integer.parseInt(id3v2Tag.getTrack().substring(0, 2)));
        song.setTitle(id3v2Tag.getTitle());
        song.setArtist(id3v2Tag.getArtist());
        song.setAlbum(id3v2Tag.getAlbum());
        song.setGenre(id3v2Tag.getGenreDescription());
        song.setComposer(id3v2Tag.getComposer());
        song.setYear(Integer.parseInt(id3v2Tag.getYear()));
        song.setDuration(Duration.ofMillis(mp3File.getLengthInMilliseconds()));
        song.setBitrate(mp3File.getBitrate());
        song.setSampleRate(mp3File.getSampleRate());
        song.setAlbumArt(id3v2Tag.getAlbumImage());
        song.setPath(file.getAbsolutePath());
        return song;
    }
}
