import dao.SongDAOImpl;
import entity.Category;
import entity.Song;
import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SongDAOImplTest {

    private static SongDAOImpl songDAO;
    private static List<Song> initialSongs;
    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void init() {
        songDAO = new SongDAOImpl();
        buildSessionFactoryForTests();
        songDAO.setSessionFactory(sessionFactory);
        initialSongs = getSongsForTests();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        for(Song song : initialSongs)
            session.save(song);
        transaction.commit();
        session.close();
    }

    private static void buildSessionFactoryForTests() {
        try
        {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate_for_tests.cfg.xml")
                    .build();

            Metadata metaData = new MetadataSources(standardRegistry)
                    .getMetadataBuilder()
                    .build();

            sessionFactory = metaData.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static List<Song> getSongsForTests() {
        Song song1 = new Song();
        song1.setTitle("Nothing else matters");
        song1.setAuthor("Metallica");
        song1.setAlbum("Metallica");
        song1.setCategory(Category.ROCK);
        song1.setVotes(15487);

        Song song2 = new Song();
        song2.setTitle("Levels");
        song2.setAuthor("Avicci");
        song2.setAlbum("Levels");
        song2.setCategory(Category.ELECTRONIC);
        song2.setVotes(23784);

        Song song3 = new Song();
        song3.setTitle("I Drive Your Truck");
        song3.setAuthor("Lee Brice");
        song3.setAlbum("Hard 2 Lov");
        song3.setCategory(Category.COUNTRY);
        song3.setVotes(9587);

        List<Song> songs = new ArrayList<>();
        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        return songs;
    }

    @Test
    public void getAll(){
        assertEquals(initialSongs, songDAO.getAll());
    }

    @Test
    public void get_SONG_IS_IN_DATABASE(){
        Song song = songDAO.get("Levels", "Avicci", "Levels");
        assertNotNull(song);
    }

    @Test
    public void get_SONG_IS_NOT_IN_DATABASE(){
        Song song = songDAO.get("Tears don't fall", "BFMV", "The Poison");
        assertNull(song);
    }

    @Test
    public void getByCategory_ALL_SONGS_WITH_GIVEN_CATEGORY() {
        List<Song> allSongs = songDAO.getAll();
        List<Song> songsWithGivenCategory = songDAO.getByCategory(Category.ROCK);
        int actual = songsWithGivenCategory.size();
        allSongs.removeAll(songsWithGivenCategory);
        int expected = songDAO.getAll().size() - allSongs.size();
        assertEquals(expected, actual);
    }

    @Test
    public void save() {
        Song expected = new Song("4", "4", "4", Category.ROCK, 458);
        songDAO.save(expected);
        Song actual = songDAO.get(expected.getTitle(), expected.getAuthor(), expected.getAlbum());
        assertEquals(expected, actual);
    }

    @Test
    public void saveAll() {
        int expected = songDAO.getAll().size() + 2;
        Song song1 = new Song("5", "5", "5", Category.ROCK, 458);
        Song song2 = new Song("6", "6", "6", Category.ROCK, 458);
        List<Song> songs = new ArrayList<>();
        songs.add(song1);
        songs.add(song2);
        songDAO.saveAll(songs);
        int actual = songDAO.getAll().size();
        assertEquals(expected,actual);
    }

    @Test
    public void setVotes() {
        Song songToUpdate = initialSongs.get(0);
        String title = songToUpdate.getTitle();
        String author = songToUpdate.getAuthor();
        String album = songToUpdate.getAuthor();
        songDAO.setVotes(title, author, album, 0);
        Song song = songDAO.get(title, author, album);
        int expected = 0;
        int actual = song.getVotes();
        assertEquals(expected, actual);
    }

    @Test
    public void getTopSong(){
        List<Song> top1 = songDAO.getTopSong(1);
        List<Song> top2 = songDAO.getTopSong(2);
        List<Song> top3 = songDAO.getTopSong(3);
        top3.removeAll(top2);
        top2.removeAll(top1);
        assertTrue(top1.get(0).getVotes() > top2.get(0).getVotes() &&
                top2.get(0).getVotes() > top3.get(0).getVotes());
    }

    @Test
    public void nullifyAllSongsVotes(){
        songDAO.nullifyAllSongsVotes();
        int expected = 0;
        int actual;
        for(Song song : songDAO.getAll()) {
            actual = song.getVotes();
            assertEquals(expected, actual);
        }
    }
}
