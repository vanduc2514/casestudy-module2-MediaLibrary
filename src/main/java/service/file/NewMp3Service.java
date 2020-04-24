package main.java.service.file;/*
 * @project caseStudy-module2-MediaLibrary
 * @author Duc on 4/24/2020
 */

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import main.java.model.AlbumDao;
import main.java.model.ArtistDao;
import main.java.model.GenreDao;
import main.java.model.SongDao;
import main.java.service.dao.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class NewMp3Service {
//    private ArtistManager artistManager = new ArtistManagerImp();
//    private AlbumManager albumManager = new AlbumManagerImp();
//    private GenreManager genreManager = new GenreManagerImp();
//    private SongDaoManagerImp songDaoManagerImp = new SongDaoManagerImp();
//    private ArtistDao artistDao;
//    private AlbumDao albumDao;
//    private GenreDao genreDao;
//
//    public void importSong(File file, SongDaoManager SongDaoManager) {
////        try {
////            Mp3File mp3File = new Mp3File(file);
////            ID3v2 id3v2 = mp3File.getId3v2Tag();
////            SongDao songDao = new SongDao();
////            artistDao = new ArtistDao(id3v2.getArtist());
////            albumDao = new AlbumDao(id3v2.getAlbum());
////            genreDao = new GenreDao(id3v2.getGenreDescription());
////            if (!artistManager.contains(artistDao)) {
////                artistManager.addArtist(artistDao);
////            } else {
////                artistDao = artistManager.getArtist(id3v2.getArtist());
////            }
////            if (!albumManager.contains(albumDao)) {
////                albumManager.addAlbum(albumDao);
////            } else {
////                albumDao = albumManager.getAlbum(id3v2.getAlbum());
////            }
////            if (!genreManager.contains(genreDao)) {
////                genreManager.addGenre(genreDao);
////            } else {
////                genreDao = genreManager.getGenre(id3v2.getGenreDescription());
////            }
////            artistDao.addSongDao(songDao);
////            albumDao.addSongDao(songDao);
////            genreDao.addSongDao(songDao);
////            songDao.setComposer(id3v2.getComposer());
////            songDao.setYear(id3v2.getYear());
////            songDaoManagerImp.addSongDao(songDao);
////        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
////            e.printStackTrace();
////        }
////
////    }
////
////    public void saveList(File file, SongDaoManager SongDaoManager) {
////
////    }
////
////    public SongDaoManager readList(File file) {
////        return null;
////    }
////
////    public void setMedata(File file, HashMap<String, String> propertyMap) {
////
////    }
}
