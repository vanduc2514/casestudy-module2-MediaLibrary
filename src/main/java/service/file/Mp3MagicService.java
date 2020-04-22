package main.java.service.file;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/22/2020
 */

import com.mpatric.mp3agic.*;
import main.java.model.Song;

import java.io.*;
import java.net.URLConnection;
import java.time.Duration;
import java.util.HashMap;

//Concrete Class
public class Mp3MagicService extends Mp3Service {
    @Override
    public Song parseSong(File file) {
        Song song = new Song();
        Mp3File mp3File;
        try {
            mp3File = new Mp3File(file);
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            song.setTrackNumber(id3v2Tag.getTrack());
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
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            e.printStackTrace();
        }
        return song;
    }

    @Override
    public void setMedata(File file, HashMap<String, String> propertyMap) {
        Mp3File mp3File = null;
        try {
            mp3File = new Mp3File(file);
            ID3v2 id3v2Tag = mp3File.getId3v2Tag();
            id3v2Tag.setTrack(propertyMap.get("track"));
            id3v2Tag.setTitle(propertyMap.get("title"));
            id3v2Tag.setArtist(propertyMap.get("artist"));
            id3v2Tag.setAlbum(propertyMap.get("album"));
            id3v2Tag.setGenreDescription(propertyMap.get("genre"));
            id3v2Tag.setComposer(propertyMap.get("composer"));
            id3v2Tag.setYear(propertyMap.get("year"));
            setAlbumArt(file, propertyMap, id3v2Tag);
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            e.printStackTrace();
        }
        try {
            assert mp3File != null;
            mp3File.save(file.getAbsolutePath() + "modified");
            renameModifiedFile(file);
        } catch (IOException | NotSupportedException e) {
            e.printStackTrace();
        }

    }

    private void setAlbumArt(File file, HashMap<String, String> hashMap, ID3v2 id3v2Tag) throws IOException {
        if (hashMap.get("albumArtPath") != null) {
            File albumArtPath = new File(hashMap.get("albumArtPath"));
            if (!albumArtPath.exists()) {
                throw new FileNotFoundException("File not exits!");
            }
            InputStream inputStream = new FileInputStream(albumArtPath);
            byte[] albumArt = new byte[(int) file.length()];
            if (inputStream.read(albumArt) != -1) {
                String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
                id3v2Tag.setAlbumImage(albumArt, mimeType);
            }
        }
    }

    private void renameModifiedFile(File file) throws IOException {
        if (file.delete()) {
            File modifiedFile = new File(file.getAbsolutePath() + "modified");
            if (!modifiedFile.renameTo(new File(file.getAbsolutePath()))) {
                throw new IOException("Error!");
            }
        }
    }

}
