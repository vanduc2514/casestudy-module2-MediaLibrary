package controllers.sorter;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/24/2020
 */

//Strategy Pattern
public interface Sorter {
    void sortByTrackNatural();

    void sortByTrackReverse();

    void sortByTitleNatural();

    void sortByTitleReverse();

    void sortByArtistNatural();

    void sortByArtistReverse();

    void sortByAlbumNatural();

    void sortByAlbumReverse();

    void sortByGenreNatural();

    void sortByGenreReverse();

    void sortByYearNatural();

    void sortByYearReverse();

    void sortByComposerNatural();

    void sortByComposerReverse();

    void sortByDurationNatural();

    void sortByDurationReverse();
}
