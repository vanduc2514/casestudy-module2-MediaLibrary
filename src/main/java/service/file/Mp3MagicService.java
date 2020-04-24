package main.java.service.file;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/25/2020
 */

import com.mpatric.mp3agic.*;
import main.java.model.*;

import java.io.*;
import java.net.URLConnection;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mp3MagicService extends Mp3Service {
    private List<SongData> artistDaoList;
    private List<SongData> albumDaoList;
    private List<SongData> genreDaoList;

    public Mp3MagicService() {
        artistDaoList = new ArrayList<>();
        albumDaoList = new ArrayList<>();
        genreDaoList = new ArrayList<>();
    }

    public List<SongData> getAlbumDaoList() {
        return albumDaoList;
    }

    public List<SongData> getArtistDaoList() {
        return artistDaoList;
    }

    public List<SongData> getGenreDaoList() {
        return genreDaoList;
    }

    @Override
    public SongDao parseSong(File file) {
        SongDao songDao = new SongDao();
        try {
            Mp3File mp3File = new Mp3File(file);
            ID3v2 id3v2 = mp3File.getId3v2Tag();
            if (id3v2.getTrack() != null) {
                songDao.setTrackNumber(id3v2.getTrack());
            }
            if (id3v2.getTitle() != null) {
                songDao.setTitle(id3v2.getTitle());
            }
            if (id3v2.getArtist() != null) {
                SongData artistDao = new ArtistDao(id3v2.getArtist());
                if (!artistDaoList.contains(artistDao)) {
                    artistDaoList.add(artistDao);
                } else {
                    artistDao = artistDaoList.get(artistDaoList.indexOf(artistDao));
                }
                songDao.setArtistDao(artistDao);
                artistDao.getSongDaoList().add(songDao);
            }
            if (id3v2.getAlbum() != null) {
                SongData albumDao = new AlbumDao(id3v2.getAlbum());
                if (!albumDaoList.contains(albumDao)) {
                    albumDaoList.add(albumDao);
                } else {
                    albumDao = albumDaoList.get(albumDaoList.indexOf(albumDao));
                }
                songDao.setAlbumDao(albumDao);
                albumDao.getSongDaoList().add(songDao);
            }
            if (id3v2.getGenreDescription() != null) {
                SongData genreDao = new GenreDao(id3v2.getGenreDescription());
                if (!genreDaoList.contains(genreDao)) {
                    genreDaoList.add(genreDao);
                } else {
                    genreDao = genreDaoList.get(genreDaoList.indexOf(genreDao));
                }
                songDao.setGenreDao(genreDao);
                genreDao.getSongDaoList().add(songDao);
            }
            if (id3v2.getComposer() != null) {
                songDao.setComposer(id3v2.getComposer());
            }
            if (id3v2.getYear() != null) {
                songDao.setYear(Integer.parseInt(id3v2.getYear()));
            }
            if (id3v2.getAlbumImage() != null) {
                songDao.setAlbumArt(id3v2.getAlbumImage());
            }
            songDao.setDuration(Duration.ofMillis(mp3File.getLengthInMilliseconds()));
            songDao.setBitrate(mp3File.getBitrate());
            songDao.setSampleRate(mp3File.getSampleRate());
            songDao.setPath(file.getAbsolutePath());
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            e.printStackTrace();
        }
        return songDao;
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
            if (propertyMap.get("albumPath") != null) {
                byte[] albumArt = getAlbumArt(propertyMap);
                id3v2Tag.setAlbumImage(albumArt, getMimeType(albumArt));
            }
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

    private byte[] getAlbumArt(HashMap<String, String> hashMap) throws IOException, NullPointerException {
        File albumArtFile = new File(hashMap.get("albumArtPath"));
        if (!albumArtFile.exists()) {
            throw new FileNotFoundException("File not exits!");
        }
        InputStream inputStream = new FileInputStream(albumArtFile);
        byte[] albumArt = new byte[(int) albumArtFile.length()];
        if (inputStream.read(albumArt) != -1) {
            return albumArt;
        }
        return null;
    }

    private String getMimeType(byte[] byteData) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteData);
        return URLConnection.guessContentTypeFromStream(inputStream);
    }

    private void renameModifiedFile(File file) throws IOException {
        if (file.delete()) {
            File modifiedFile = new File(file.getAbsolutePath() + "modified");
            if (!modifiedFile.renameTo(new File(file.getAbsolutePath()))) {
                throw new IOException("Error!");
            }
        } else {
            throw new IOException();
        }
    }
}
