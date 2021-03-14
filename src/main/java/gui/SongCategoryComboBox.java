package gui;

import entity.Category;

import javax.swing.*;
import java.util.Arrays;

public class SongCategoryComboBox extends JComboBox<String> {

    public SongCategoryComboBox() {
        super(Arrays.toString(Category.values()).replaceAll("^.|.$", "").split(", "));
        init();
    }

    private void init() {
        insertItemAt("All categories", 0);
        setSelectedIndex(0);
        setOpaque(true);
    }
}
