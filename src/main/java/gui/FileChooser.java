package gui;

import entity.Song;
import io.SongReaderImpl;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileChooser extends JFileChooser {


    private SongReaderImpl songReader;
    private static final String unsupportedFileFormatMessage =
            " files extensions aren't supported by this application. Use .xml or .csv extensions!";

    public FileChooser() {
        init();
    }

    private void init() {
        FileFilter xmlFilter = new FileNameExtensionFilter("Song", "xml", "csv");
        setFileFilter(xmlFilter);
        songReader = new SongReaderImpl();
        setMultiSelectionEnabled(true);
    }

    public List<Song> getSongsFromFile() {
        List<Song> songs = new LinkedList<>();
        List<Song> correctSongs;
        File[] selectedFiles = getSelectedFiles();
        for(File file : selectedFiles) {
            String fileExtension = getFileExtension(file);
            switch (fileExtension) {
                case "xml":
                    songs.addAll(songReader.readFromXML(file));
                    break;
                case "csv":
                    songs.addAll(songReader.readFromCSV(file));
                    break;
                default:
                    showUnsupportedFileMessage(fileExtension);
            }
        }
        correctSongs = getCorrectSongs(songs);
        return correctSongs;
    }

    private String getFileExtension(File file) {
        return FilenameUtils.getExtension(file.getName());
    }

    private void showUnsupportedFileMessage(String fileExtension) {
        JOptionPane.showMessageDialog(new JFrame(),
                "\"." + fileExtension + "\" " + unsupportedFileFormatMessage,
                "Dialog",
                JOptionPane.ERROR_MESSAGE);
    }

    private List<Song> getCorrectSongs(List<Song> songs){
        List<Song> correctSongs = new ArrayList<>();
        List<Song> incorrectSongs = new ArrayList<>();

        for(Song song : songs){
            if(isIncorrectSong(song))
                incorrectSongs.add(song);
            else correctSongs.add(song);
        }
        if(incorrectSongs.size() != 0)
            showIncorrectSongsMessage(incorrectSongs);
        return correctSongs;
    }

    private boolean isIncorrectSong(Song song) {
        return song.getTitle() == null ||
                song.getAuthor() == null ||
                song.getAlbum() == null ||
                song.getCategory() == null ||
                song.getVotes() < 1;
    }

    private void showIncorrectSongsMessage(List<Song> songs) {
        String formattedIncorrectSongsMessage = getFormattedIncorrectSongsMessage(songs);
        JOptionPane.showMessageDialog(this,
                formattedIncorrectSongsMessage,
                "Dialog",
                JOptionPane.ERROR_MESSAGE);
    }

    private String getFormattedIncorrectSongsMessage(List<Song> songs) {
        StringBuilder builder = new StringBuilder();
        builder.append("Incorrect songs \n");
        for(Song song : songs) {
            builder.append(song.getAuthor())
                    .append(" - ")
                    .append(song.getTitle())
                    .append(" (album - ")
                    .append(song.getAlbum())
                    .append("; category - ")
                    .append(song.getCategory())
                    .append("; votes - ")
                    .append(song.getVotes())
                    .append(")\n");
        }
        builder.append("These songs will not be saved!");
        return builder.toString();
    }
}
