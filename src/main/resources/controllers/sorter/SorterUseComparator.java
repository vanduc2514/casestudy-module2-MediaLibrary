package main.resources.controllers.sorter;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/24/2020
 */

import javafx.collections.ObservableList;
import main.java.model.Song;

import java.util.Comparator;

//Proxy Pattern
public class SorterUseComparator implements Sorter {
    private ObservableList<Song> observableList;

    public SorterUseComparator(ObservableList<Song> observableList) {
        this.observableList = observableList;
    }

    public void sortByTrackNatural() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song1.getTrackNumber().compareTo(song2.getTrackNumber());
            }
        });
    }

    public void sortByTrackReverse() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song2.getTrackNumber().compareTo(song1.getTrackNumber());
            }
        });
    }

    public void sortByTitleNatural() {
        observableList.sort(Comparator.naturalOrder());
    }

    public void sortByTitleReverse() {
        observableList.sort(Comparator.reverseOrder());
    }

    public void sortByArtistNatural() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song1.getArtist().compareTo(song2.getArtist());
            }
        });
    }

    public void sortByArtistReverse() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song2.getArtist().compareTo(song1.getArtist());
            }
        });
    }

    public void sortByAlbumNatural() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song1.getAlbum().compareTo(song2.getAlbum());
            }
        });
    }

    public void sortByAlbumReverse() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song1.getAlbum().compareTo(song2.getAlbum());
            }
        });
    }

    public void sortByGenreNatural() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song1.getGenre().compareTo(song2.getGenre());
            }
        });
    }

    public void sortByGenreReverse() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song2.getGenre().compareTo(song1.getGenre());
            }
        });
    }

    public void sortByYearNatural() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song1.getYear() - song2.getYear();
            }
        });
    }

    public void sortByYearReverse() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song2.getYear() - song1.getYear();
            }
        });
    }

    public void sortByComposerNatural() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song1.getComposer().compareTo(song2.getComposer());
            }
        });
    }

    public void sortByComposerReverse() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song2.getComposer().compareTo(song1.getComposer());
            }
        });
    }

    public void sortByDurationNatural() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song1.getDuration().compareTo(song2.getDuration());
            }
        });
    }

    public void sortByDurationReverse() {
        observableList.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song2.getDuration().compareTo(song1.getDuration());
            }
        });
    }
}
