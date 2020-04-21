package main.java.model;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/18/2020
 */

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;

public class Song implements Serializable {
    private int trackNumber;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private String creator;
    private int year;
    private Duration duration;
    private int bitrate;
    private int sampleRate;
    private byte[] albumArt;

    public Song() {
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
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

    public String getCreator() {
        return creator;
    }

    public void setComposer(String creator) {
        this.creator = creator;
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

    public String getDurationString() {
        return duration.toMinutes() + ":" + duration.minusMinutes(duration.toMinutes()).getSeconds();
    }

    public byte[] getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(byte[] albumArt) {
        this.albumArt = albumArt;
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
