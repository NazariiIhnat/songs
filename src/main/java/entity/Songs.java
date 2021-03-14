package entity;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="songs")
public class Songs{

    @XmlElement(name="song", type = Song.class)
    private List<Song> songs = new ArrayList<>();

    public Songs(List<Song> songs) {
        this.songs = songs;
    }

    public Songs() {
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
