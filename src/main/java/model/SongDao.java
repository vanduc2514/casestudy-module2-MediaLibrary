package main.java.model;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/24/2020
 */

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

public class SongDao implements Serializable, Comparable<SongDao> {
    private String trackNumber;
    private String title;
    private SongData artistDao;
    private SongData albumDao;
    private SongData genreDao;
    private String composer;
    private int year;
    private Duration duration;
    private int bitrate;
    private int sampleRate;
    private byte[] albumArt;
    private String path;
    private LocalDateTime importTime;

    public SongDao() {
        trackNumber = "";
        title = "";
        artistDao = new ArtistDao();
        albumDao = new AlbumDao();
        genreDao = new GenreDao();
        composer = "";
        year = 0;
        duration = Duration.ZERO;
        bitrate = 0;
        sampleRate = 0;
        albumArt = new byte[0];
        path = "";
        importTime = LocalDateTime.now();
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SongData getArtistDao() {
        return artistDao;
    }

    public void setArtistDao(SongData artistDao) {
        this.artistDao = artistDao;
    }

    public SongData getAlbumDao() {
        return albumDao;
    }

    public void setAlbumDao(SongData albumDao) {
        this.albumDao = albumDao;
    }

    public SongData getGenreDao() {
        return genreDao;
    }

    public void setGenreDao(SongData genreDao) {
        this.genreDao = genreDao;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String creator) {
        this.composer = creator;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public byte[] getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(byte[] albumArt) {
        this.albumArt = albumArt;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getImportTime() {
        return importTime;
    }

    @Override
    public int compareTo(SongDao anotherSongDao) {
        return title.compareTo(anotherSongDao.getTitle());
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        SongDao songDao = (SongDao) obj;
        return (title.equals(songDao.getTitle())
                && artistDao.getTitle().equals(songDao.getArtistDao().getTitle())
                && albumDao.getTitle().equals(songDao.getAlbumDao().getTitle()));
    }

    @Override
    public String toString() {
        return "SongDao{" +
                "trackNumber='" + trackNumber + '\'' +
                ", title='" + title + '\'' +
                ", artistDao=" + artistDao.getTitle() +
                ", albumDao=" + albumDao.getTitle() +
                ", genreDao=" + genreDao.getTitle() +
                ", composer='" + composer + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                ", bitrate=" + bitrate +
                ", sampleRate=" + sampleRate +
                ", albumArt=" + Arrays.toString(albumArt) +
                ", path='" + path + '\'' +
                ", importTime=" + importTime +
                '}';
    }
}
