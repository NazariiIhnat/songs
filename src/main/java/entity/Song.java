package entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import io.CategoryCSVAdapter;
import io.CategoryXMLAdapter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="Songs")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "song")
public class Song implements Serializable {

    @Id@CsvBindByName(column = "Title")
    private String title;

    @Id@CsvBindByName(column = "Author")
    private String author;

    @Id@CsvBindByName(column = "Album")
    private String album;

    @Enumerated(EnumType.STRING)
    @XmlJavaTypeAdapter(CategoryXMLAdapter.class)
    @CsvCustomBindByName(column = "Category", converter = CategoryCSVAdapter.class)
    private Category category;

    @CsvBindByName(column = "Votes")
    private int votes;

    public Song() {
    }

    //used for ID
    public Song(String title, String author, String album) {
        this.title = title;
        this.author = author;
        this.album = album;
    }

    public Song(String title, String author, String album, Category category, int votes) {
        this.title = title;
        this.author = author;
        this.album = album;
        this.category = category;
        this.votes = votes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return votes == song.votes &&
                title.equals(song.title) &&
                author.equals(song.author) &&
                album.equals(song.album) &&
                category == song.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, album, category, votes);
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", album='" + album + '\'' +
                ", category=" + category +
                ", votes=" + votes +
                '}';
    }
}
