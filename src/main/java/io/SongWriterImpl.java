package io;

import com.opencsv.CSVWriter;
import entity.Category;
import entity.Song;
import entity.Songs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.List;

public class SongWriterImpl implements SongWriter {

    @Override
    public void writeToXML(File file, List<Song> songList) {
        try {
            JAXBContext context = JAXBContext.newInstance(Songs.class);
            Marshaller marshaller = context.createMarshaller();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            Songs songs = new Songs(songList);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(songs, writer);
            writer.flush();
            writer.close();
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToCSV(File file, List<Song> songs) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(file), ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            writeHeaders(writer);
            for(Song song : songs) {
                writer.writeNext(new String[]{song.getTitle(),
                        song.getAuthor(),
                        song.getAlbum(),
                        song.getCategory().toString(),
                        String.valueOf(song.getVotes())});
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeHeaders(CSVWriter writer) {
        String[] header = {"Title", "Author", "Album", "Category", "Votes"};
        writer.writeNext(header);
    }

    @Override
    public String getFormattedXMLString(List<Song> songList) {
        Songs songs = new Songs();
        StringWriter writer = new StringWriter();
        songs.setSongs(songList);
        JAXBContext context;
        Marshaller marshaller;
        try {
            context = JAXBContext.newInstance(Songs.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(songs, writer);
            writer.flush();
            writer.close();
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    @Override
    public String getFormattedCSVString(List<Song> songs) {
        String[] headers = {"Title", "Author", "Album", "Category", "Votes"};
        StringWriter stringWriter = new StringWriter();
        CSVWriter writer = new CSVWriter(stringWriter,
                ',',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.NO_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        writer.writeNext(headers);
        for(Song song : songs){
            String title = song.getTitle();
            String author = song.getAuthor();
            String album = song.getAlbum();
            String category = song.getCategory().toString();
            String votes = String.valueOf(song.getVotes());
            writer.writeNext(new String[]{title, author, album, category, votes});
        }
        String formattedCSVString = stringWriter.toString();
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formattedCSVString;
    }
}
