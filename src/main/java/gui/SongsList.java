package gui;

import entity.Song;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SongsList extends JScrollPane {


    private int lastSongsListOutputIndex = 4;
    public static final int TOP_3 = 1;
    public static final int TOP_10 = 2;
    public static final int SHOW_BY_CATEGORIES = 3;
    public static final int SHOW_ALL = 4;

    private JList<Song> songsList = new JList<>();

    public SongsList() {
        songsList.setCellRenderer(songListCellRenderer());
    }

    public void showSongs(List<Song> songs) {
        setViewportView(songsList);
        DefaultListModel<Song> model = new DefaultListModel<>();

        for (Song song : songs) {
            model.addElement(song);
        }
        songsList.setModel(model);
    }

    private DefaultListCellRenderer songListCellRenderer(){
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Song) {
                    Song song = (Song) value;
                    ((JLabel) renderer).setText(++index
                            + ". "
                            + song.getAuthor()
                            + " - "
                            + song.getTitle()
                            + " (album - "
                            + song.getAlbum()
                            + "; category - "
                            + song.getCategory()
                            + ", votes - "
                            + song.getVotes()
                            + ".)");
                }
                return renderer;
            }
        };
    }

    public List<Song> getSelectedSongs() {
        return songsList.getSelectedValuesList();
    }

    public boolean isSelected(){
        return !songsList.isSelectionEmpty();
    }

    public int getLastSongsListOutputIndex() {
        return lastSongsListOutputIndex;
    }

    public void setLastSongsListOutputIndex(int lastSongsListOutputIndex) {
        this.lastSongsListOutputIndex = lastSongsListOutputIndex;
    }
}
