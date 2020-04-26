package controllers.sorter;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/24/2020
 */

import javafx.collections.ObservableList;
import model.SongDao;
import java.util.Comparator;

//Proxy Pattern
public class SorterUseComparator implements Sorter {
    private ObservableList<SongDao> observableList;

    public SorterUseComparator(ObservableList<SongDao> observableList) {
        this.observableList = observableList;
    }

    public void sortByTrackNatural() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao1.getTrackNumber().compareTo(SongDao2.getTrackNumber());
            }
        });
    }

    public void sortByTrackReverse() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao2.getTrackNumber().compareTo(SongDao1.getTrackNumber());
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
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao1.getArtistDao().compareTo(SongDao2.getArtistDao());
            }
        });
    }

    public void sortByArtistReverse() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao2.getArtistDao().compareTo(SongDao1.getArtistDao());
            }
        });
    }

    public void sortByAlbumNatural() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao1.getAlbumDao().compareTo(SongDao2.getAlbumDao());
            }
        });
    }

    public void sortByAlbumReverse() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao1.getAlbumDao().compareTo(SongDao2.getAlbumDao());
            }
        });
    }

    public void sortByGenreNatural() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao1.getGenreDao().compareTo(SongDao2.getGenreDao());
            }
        });
    }

    public void sortByGenreReverse() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao2.getGenreDao().compareTo(SongDao1.getGenreDao());
            }
        });
    }

    public void sortByYearNatural() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao1.getYear() - SongDao2.getYear();
            }
        });
    }

    public void sortByYearReverse() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao2.getYear() - SongDao1.getYear();
            }
        });
    }

    public void sortByComposerNatural() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao1.getComposer().compareTo(SongDao2.getComposer());
            }
        });
    }

    public void sortByComposerReverse() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao2.getComposer().compareTo(SongDao1.getComposer());
            }
        });
    }

    public void sortByDurationNatural() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao1.getDuration().compareTo(SongDao2.getDuration());
            }
        });
    }

    public void sortByDurationReverse() {
        observableList.sort(new Comparator<SongDao>() {
            @Override
            public int compare(SongDao SongDao1, SongDao SongDao2) {
                return SongDao2.getDuration().compareTo(SongDao1.getDuration());
            }
        });
    }
}
