package main.java.model;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/18/2020
 */

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public class Song implements Serializable, Comparable<Song> {
    private String trackNumber;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private String composer;
    private int year;
    private Duration duration;
    private int bitrate;
    private int sampleRate;
    private byte[] albumArt;
    private String path;
    private LocalDateTime importTime;

    public Song() {
        trackNumber = "";
        title = "";
        artist = "";
        album = "";
        genre = "";
        composer = "";
        year = 0;
        duration = null;
        bitrate = 0;
        sampleRate = 0;
        albumArt = null;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        Song anotherSong = (Song) obj;
        return (this.trackNumber.equals(anotherSong.trackNumber)
                && this.title.equals(anotherSong.title)
                && this.artist.equals(anotherSong.artist)
                && this.album.equals(anotherSong.album)
                && this.genre.equals(anotherSong.genre)
                && this.composer.equals(anotherSong.composer)
                && this.year == anotherSong.year);
    }

    @Override
    public int compareTo(Song anotherSong) {
        return title.compareTo(anotherSong.getTitle());
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
