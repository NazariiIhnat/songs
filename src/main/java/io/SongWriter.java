package io;

import entity.Song;

import java.io.File;
import java.util.List;

public interface SongWriter {

    void writeToXML(File file, List<Song> songs);

    void writeToCSV(File file, List<Song> songs);

    String getFormattedXMLString(List<Song> songs);

    String getFormattedCSVString(List<Song> songs);
}
