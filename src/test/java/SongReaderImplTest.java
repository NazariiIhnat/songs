import entity.Song;
import io.SongReader;
import io.SongReaderImpl;
import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.util.List;

public class SongReaderImplTest {

    private static SongReader songReader = new SongReaderImpl();
    private static final String folderWithTestFiles = "src/main/resources/test/";
    private StringBuilder stringBuilder = new StringBuilder(folderWithTestFiles);

    private File getFile(String fileName){
        stringBuilder.append(fileName);
        String path = stringBuilder.toString();
        return new File(path);
    }

    @Test
    public void readFromXML_CORRECT_ATTRIBUTES(){
        String fileName = "correct attributes.xml";
        File file = getFile(fileName);
        List<Song> songList = songReader.readFromXML(file);
        int expected = 3;
        int actual = songList.size();
        assertEquals(expected, actual);
    }

    @Test
    public void readFromXML_MISSING_TITLE() {
        String fileName = "missing title.xml";
        File file = getFile(fileName);
        List<Song> songs = songReader.readFromXML(file);
        for(Song song : songs)
            assertNull(song.getTitle());
    }

    @Test
    public void readFromXML_MISSING_ALL_ATTRIBUTES() {
        String fileName = "missing all attributes.xml";
        File file = getFile(fileName);
        List<Song> songs = songReader.readFromXML(file);
        for(Song song : songs)
            assertTrue(song.getTitle() == null
            && song.getAuthor() == null
            && song.getAlbum() == null
            && song.getCategory() == null
            && song.getVotes() == 0);
    }

    @Test
    public void readFromXML_INCORRECT_CATEGORY() {
        String fileName = "incorrect category.xml";
        File file = getFile(fileName);
        List<Song> songs = songReader.readFromXML(file);
        Song song = songs.get(0);
        assertNull(song.getCategory());
    }

    @Test
    public void readFromXML_STRING_VOTES() {
        String fileName = "string votes.xml";
        File file = getFile(fileName);
        List<Song> songs = songReader.readFromXML(file);
        Song song = songs.get(0);
        int expected = 0;
        int actual = song.getVotes();
        assertEquals(expected, actual);
    }

    @Test
    public void readFromCSV_CORRECT_ATTRIBUTES() {
        String fileName = "correct attributes.csv";
        File file = getFile(fileName);
        List<Song> songs = songReader.readFromCSV(file);
        int expected = 3;
        int actual = songs.size();
        assertEquals(expected, actual);
    }

    @Test
    public void readFromCSV_MISSING_TITLE() {
        String fileName = "missing title.csv";
        File file = getFile(fileName);
        List<Song> songs = songReader.readFromCSV(file);
        for(Song song : songs)
            assertNull(song.getTitle());
    }

    @Test
    public void readFromCSV_MISSING_ALL_ATTRIBUTES() {
        String fileName = "missing all attributes.csv";
        File file = getFile(fileName);
        List<Song> songs = songReader.readFromCSV(file);
        int expected = 0;
        int actual = songs.size();
        assertEquals(expected, actual);
    }

    @Test
    public void readFromCSV_INCORRECT_CATEGORY() {
        String fileName = "incorrect category.csv";
        File file = getFile(fileName);
        List<Song> songs = songReader.readFromCSV(file);
        Song song = songs.get(0);
        assertNull(song.getCategory());
    }

    @Test
    public void readFromCSV_STRING_VOTES() {
        String fileName = "string votes.csv";
        File file = getFile(fileName);
        List<Song> songs = songReader.readFromCSV(file);
        for(Song song : songs)
            assertEquals(0, song.getVotes());
    }

    @Test
    public void readFromCSV_ADDITIONAL_SEPARATOR() {
        String fileName = "additional separator.csv";
        File file = getFile(fileName);
        List<Song> songs = songReader.readFromCSV(file);
        int expected = 3;
        int actual = songs.size();
        assertEquals(expected, actual);
    }
}
