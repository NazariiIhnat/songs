package io;

import com.opencsv.bean.BeanVerifier;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvException;
import entity.Category;
import entity.Song;
import entity.Songs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SongReaderImpl implements SongReader {

    @Override
    public List<Song> readFromXML(File file) {
        Songs songs = null;
        try {
            JAXBContext context = JAXBContext.newInstance(Songs.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            songs = (Songs) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        assert songs != null;
        return songs.getSongs();
    }

    @Override
    public List<Song> readFromCSV(File file) {
        List<Song> songs = null;
        CsvToBean<Song> builder;
        try {
            Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            builder = new CsvToBeanBuilder<Song>(reader)
                    .withType(Song.class)
                    .withSeparator(',')
                    .withExceptionHandler(new BadCSVValuesFormatExceptionHandler())
                    .build();
            songs = builder.parse();
            if(builder.getCapturedExceptions().size() != 0) {
                List<Song> songsWithNulls = buildSongsWithNullValuesFromCapturedExceptions(builder);;
                songs.addAll(songsWithNulls);
                builder.getCapturedExceptions().clear();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }

    private List<Song> buildSongsWithNullValuesFromCapturedExceptions(CsvToBean<Song> builder) {
        List<Song> songsWithNullValues = new ArrayList<>();
        for(CsvException exception : builder.getCapturedExceptions())
            songsWithNullValues.add(getSongFromException(exception));
        return songsWithNullValues;
    }


    private Song getSongFromException(CsvException e){
        String title = e.getLine()[0];
        String author = e.getLine()[1];
        String album = e.getLine()[2];
        Category category = Category.getCategoryByText(e.getLine()[3]);
        String stringVotes = e.getLine()[4];
        int votes;
        try{
            votes = Integer.parseInt(stringVotes);
        } catch (NumberFormatException exception){
            votes =0;
        }
        return new Song(title, author, album, category, votes);
    }
}
