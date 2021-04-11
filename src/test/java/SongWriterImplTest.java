import entity.Category;
import entity.Song;
import io.SongReader;
import io.SongReaderImpl;
import io.SongWriter;
import io.SongWriterImpl;
import org.junit.*;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SongWriterImplTest {

    private static final String PATH_OF_XML_TEST_FILE = "src/main/resources/test/test xml writer.xml";
    private static final String PATH_OF_CSV_TEST_FILE = "src/main/resources/test/test csv writer.csv";
    private static final File XML_TEST_FILE = new File(PATH_OF_XML_TEST_FILE);
    private static final File CSV_TEST_FILE = new File(PATH_OF_CSV_TEST_FILE);
    private static final SongReader SONG_READER = new SongReaderImpl();
    private static final SongWriter SONG_WRITER = new SongWriterImpl();
    private static List<Song> expectedSongs;

    @BeforeClass
    public static void initResources() {
        expectedSongs = new ArrayList<>();
        String xmlSongsPath = "src/main/resources/test/correct attributes.xml";
        File file = new File(xmlSongsPath);
        expectedSongs = SONG_READER.readFromXML(file);
    }

    @AfterClass
    public static void clearResources() {
        XML_TEST_FILE.deleteOnExit();
        CSV_TEST_FILE.deleteOnExit();
    }

    @Test
    public void writeXMLToFile(){
        SONG_WRITER.writeToXML(XML_TEST_FILE, expectedSongs);
        List<Song> actualSongs = SONG_READER.readFromXML(XML_TEST_FILE);
        assertEquals(expectedSongs, actualSongs);
    }

    @Test
    public void writeCSVToFile() {
        SONG_WRITER.writeToCSV(CSV_TEST_FILE, expectedSongs);
        List<Song> actualSongs = SONG_READER.readFromCSV(CSV_TEST_FILE);
        assertEquals(expectedSongs, actualSongs);
    }

    @Test
    public void getFormattedXMLString() {
        StringBuilder stringBuilder = new StringBuilder();

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";
        String openSongsTag = "<songs>\n";
        String closeSongsTag = "</songs>\n";

        String openSongTag = "    <song>\n";
        String closeSongTag = "    </song>\n";

        String openTitleTag = "        <title>";
        String closeTitleTag = "</title>\n";

        String openAuthorTag = "        <author>";
        String closeAuthorTag = "</author>\n";

        String openAlbumTag = "        <album>";
        String closeAlbumTag = "</album>\n";

        String openCategoryTag = "        <category>";
        String closeCategoryTag = "</category>\n";

        String openVotesTag = "        <votes>";
        String closeVotesTag = "</votes>\n";

        stringBuilder.append(header).append(openSongsTag);
        for(Song song : expectedSongs)
                    stringBuilder.append(openSongTag)
                    .append(openTitleTag).append(song.getTitle()) .append(closeTitleTag)
                    .append(openAuthorTag).append(song.getAuthor()).append(closeAuthorTag)
                    .append(openAlbumTag).append(song.getAlbum()).append(closeAlbumTag)
                    .append(openCategoryTag).append(song.getCategory()).append(closeCategoryTag)
                    .append(openVotesTag).append(song.getVotes()).append(closeVotesTag)
                    .append(closeSongTag);
        stringBuilder.append(closeSongsTag);
        String expected = stringBuilder.toString();
        String actual = SONG_WRITER.getFormattedXMLString(expectedSongs);
        assertEquals(expected, actual);
    }

    @Test
    public void getFormattedCSVString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Title,Author,Album,Category,Votes\n");
        for(Song song : expectedSongs)
            stringBuilder.append(song.getTitle())
            .append(",")
            .append(song.getAuthor())
            .append(",")
            .append(song.getAlbum())
            .append(",")
            .append(song.getCategory())
            .append(",")
            .append(song.getVotes())
            .append("\n");

        String expected = stringBuilder.toString();
        String actual = SONG_WRITER.getFormattedCSVString(expectedSongs);
        assertEquals(expected, actual);
    }
}
