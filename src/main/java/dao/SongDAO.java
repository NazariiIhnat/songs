package dao;

import entity.Category;
import entity.Song;

import java.util.List;

public interface SongDAO {

     Song get(String title, String author, String album);

     List<Song> getAll();

     List<Song> getByCategory(Category category);

     void save(Song song);

     void saveAll(List<Song> songs);

     void setVotes(String title, String author, String album, int votes);

     void nullifyAllSongsVotes();

     List<Song> getTopSong(int topNumber);
}
