package dao;

import entity.Category;
import entity.Song;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class SongDAOImpl implements SongDAO{

    private SessionFactory sessionFactory;

    @Override
    public Song get(String title, String author, String album) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Song song = getSong(session, title, author, album);
        transaction.commit();
        session.close();
        return song;
    }

    private Song getSong(Session session, String title, String author, String album) {
        return session.get(Song.class, new Song(title, author, album));
    }

    @Override
    public List<Song> getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Song> songs = getAllSongs(session);
        transaction.commit();
        session.close();
        return songs;
    }

    private List<Song> getAllSongs(Session session) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Song> criteriaQuery = criteriaBuilder.createQuery(Song.class);
        criteriaQuery.from(Song.class);
        return session.createQuery(criteriaQuery).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Song> getByCategory(Category category) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(
                "from Song where category = ?1", Song.class)
                .setParameter(1, category);
        List<Song> list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public void save(Song song) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        if(songExist(song)) {
            int oldValue = get(song.getTitle(), song.getAuthor(), song.getAlbum()).getVotes();
            setVotes(song.getTitle(), song.getAuthor(), song.getAlbum(), oldValue + song.getVotes());
        }
        else session.save(song);
        transaction.commit();
        session.close();
    }

    private boolean songExist(Song song){
        return get(song.getTitle(), song.getAuthor(), song.getAlbum()) != null;
    }

    @Override
    public void saveAll(List<Song> songs) {
        for(Song song : songs)
            save(song);
    }

    @Override
    public void setVotes(String title, String author, String album, int votes) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Song songToUpdate = getSong(session,title, author, album);
        songToUpdate.setVotes(votes);
        session.update(songToUpdate);
        transaction.commit();
        session.close();
    }

    @Override
    public void nullifyAllSongsVotes() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Song> songs = getAllSongs(session);
        for(Song song : songs) {
            song.setVotes(0);
            session.update(song);
        }
        transaction.commit();
        session.close();
    }

    @Override
    public List<Song> getTopSong(int topNumber) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(
                "from Song order by Votes desc", Song.class)
                .setMaxResults(topNumber);
        List<Song> list = query.list();
        transaction.commit();
        session.close();
        return list;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
