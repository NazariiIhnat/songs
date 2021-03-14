package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import dao.SongDAOImpl;
import entity.Category;
import entity.Song;
import io.SongWriter;
import io.SongWriterImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class MainFrame extends JFrame{

    private JPanel contentPane;
    private SongsList songsList;
    private SongDAOImpl songDAO;
    private ReportDialog reportDialog;
    private FileChooser fileChooser;
    private SongWriter songWriter;
    private SongCategoryComboBox categoryComboBox;

    public MainFrame() {
        setLookAndFeel();
        init();
        List<Song> songs = songDAO.getAll();
        if(songs.size() > 0) {
            songsList.showSongs(songs);
        }
    }

    private void setLookAndFeel() {
        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        songDAO = new SongDAOImpl();
        fileChooser = new FileChooser();
        songWriter = new SongWriterImpl();
        reportDialog = new ReportDialog(this, "Report", true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 287);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        songsList = new SongsList();
        songsList.setBounds(130, 10, 444, 230);
        contentPane.add(songsList);

        JButton addSongButton = new JButton("Add song");
        addSongButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choose = fileChooser.showOpenDialog(contentPane);
                if(choose == JFileChooser.APPROVE_OPTION) {
                    List<Song> songs = fileChooser.getSongsFromFile();
                    songDAO.saveAll(songs);
                }
                showSongsAccordingToLastOperation();
            }
        });
        addSongButton.setBounds(10, 11, 110, 23);
        contentPane.add(addSongButton);

        JButton topThreeSongsButton = new JButton("TOP 3");
        topThreeSongsButton.setBounds(10, 45, 110, 23);
        topThreeSongsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                songsList.showSongs(songDAO.getTopSong(3));
                songsList.setLastSongsListOutputIndex(SongsList.TOP_3);
            }
        });
        contentPane.add(topThreeSongsButton);

        JButton topTenSongsButton = new JButton("TOP 10");
        topTenSongsButton.setBounds(10, 79, 110, 23);
        topTenSongsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                songsList.showSongs(songDAO.getTopSong(10));
                songsList.setLastSongsListOutputIndex(SongsList.TOP_10);
            }
        });
        contentPane.add(topTenSongsButton);

        categoryComboBox = new SongCategoryComboBox();
        categoryComboBox.setBounds(10, 115, 110, 23);
        categoryComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(categoryComboBox.getSelectedIndex() == 0) {
                    songsList.showSongs(songDAO.getAll());
                    songsList.setLastSongsListOutputIndex(SongsList.SHOW_ALL);
                }
                else {
                    String categoryText = categoryComboBox.getSelectedItem().toString();
                    Category category = Category.getCategoryByText(categoryText);
                    List<Song> songs = songDAO.getByCategory(category);
                    songsList.showSongs(songs);
                    songsList.setLastSongsListOutputIndex(SongsList.SHOW_BY_CATEGORIES);
                }
            }
        });
        contentPane.add(categoryComboBox);

        JButton addVoteButton = new JButton("Add vote");
        addVoteButton.setBounds(10, 149, 110, 23);
        addVoteButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(songsList.isSelected())
                    for(Song song : songsList.getSelectedSongs())
                        songDAO.setVotes(song.getTitle(), song.getAuthor(), song.getAlbum(), song.getVotes() + 1);
                showSongsAccordingToLastOperation();
            }
        });
        contentPane.add(addVoteButton);

        JButton reportButton = new JButton("Report");
        reportButton.setBounds(10, 183, 110, 23);
        reportButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportDialog.setTextToXMLTextArea(songWriter.getFormattedXMLString(songsList.getSelectedSongs()));
                reportDialog.setTextToCSVTextArea(songWriter.getFormattedCSVString(songsList.getSelectedSongs()));
                reportDialog.setSongsToSave(songsList.getSelectedSongs());
                reportDialog.setVisible(true);
            }
        });
        contentPane.add(reportButton);

        JButton clearVotesForAllSongsButton = new JButton("Clear all votes");
        clearVotesForAllSongsButton.setBounds(10, 217, 110, 23);
        clearVotesForAllSongsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(Song song : songDAO.getAll())
                    songDAO.setVotes(song.getTitle(), song.getAuthor(), song.getAlbum(), 0);
                showSongsAccordingToLastOperation();
            }
        });

        contentPane.add(clearVotesForAllSongsButton);
        setVisible(true);
    }

    private void showSongsAccordingToLastOperation(){
        switch(songsList.getLastSongsListOutputIndex()){
            case SongsList.TOP_3 : songsList.showSongs(songDAO.getTopSong(3)); break;
            case SongsList.TOP_10 : songsList.showSongs(songDAO.getTopSong(10)); break;
            case SongsList.SHOW_BY_CATEGORIES : songsList.showSongs
                    (songDAO.getByCategory
                            (Category.getCategoryByText
                                    (categoryComboBox.getSelectedItem().toString()))); break;
            case SongsList.SHOW_ALL : songsList.showSongs(songDAO.getAll()); break;
        }
    }
}
