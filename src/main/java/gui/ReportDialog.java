package gui;

import entity.Song;
import io.SongWriter;
import io.SongWriterImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

public class ReportDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextArea xmlTextArea;
    private JTextArea csvTextArea;
    private Frame owner;
    private List<Song> songsToSave;


    public ReportDialog(Frame owner, String title, boolean modal){
        super(owner, title, modal);
        this.owner = owner;
        init();
    }

    private void init(){
        SongWriter writer = new SongWriterImpl();
        setBounds(100, 100, 600, 381);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 11, 564, 286);
        contentPanel.add(tabbedPane);

        JScrollPane xmlScrollPane = new JScrollPane();
        xmlTextArea = new JTextArea();
        xmlScrollPane.setViewportView(xmlTextArea);
        xmlTextArea.setEditable(false);
        tabbedPane.addTab("XML", null, xmlScrollPane, null);

        JScrollPane csvScrollPane = new JScrollPane();
        csvTextArea = new JTextArea();
        csvScrollPane.setViewportView(csvTextArea);
        csvTextArea.setEditable(false);
        tabbedPane.addTab("CSV", null, csvScrollPane, null);

        FileChooser fileChooser = new FileChooser();
        FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML", "xml");
        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV", "csv");

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(247, 308, 80, 23);
        saveButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choose = fileChooser.showSaveDialog(owner);
                File file = fileChooser.getSelectedFile();
                if(choose == JFileChooser.APPROVE_OPTION){
                    switch(tabbedPane.getSelectedIndex()){
                        case 0 :
                            writer.writeToXML(file, getSongsToSave()); break;
                        case 1 :
                            writer.writeToCSV(file, getSongsToSave()); break;
                    }
                    dispose();
                }
            }
        });
        contentPanel.add(saveButton);
    }

    public List<Song> getSongsToSave() {
        return songsToSave;
    }

    public void setSongsToSave(List<Song> songsToSave) {
        this.songsToSave = songsToSave;
    }

    public void setTextToXMLTextArea(String text){
        xmlTextArea.setText(text);
    }

    public void setTextToCSVTextArea(String text) {
        csvTextArea.setText(text);
    }
}
