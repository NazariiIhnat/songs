package io;

import entity.Song;

import java.io.File;
import java.util.List;

public interface SongReader {

    List<Song> readFromXML(File file);

    List<Song> readFromCSV(File file);
}
