package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;

import javax.inject.Inject;
import javax.persistence.*;

public class SongDao implements ISongDao {

    @Inject
    EntityManagerFactory emf;

	@Override
	public Integer createSong(Song song) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            song.setId(null);
            transaction.begin();
            em.persist(song);
            transaction.commit();
            return song.getId();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding song: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }

	}

	@Override
	public Song getSong(Integer id) {
        EntityManager em = emf.createEntityManager();
        Song entity = null;
        try {
            entity = em.find(Song.class, id);
        } finally {
            em.close();
        }
        return entity;
	}

	@Override
	public Collection<Song> getAllSongs() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Song> query = em.createQuery("SELECT s FROM Song s", Song.class);
            return query.getResultList();
        } finally {
            em.close();
        }
	}

	@Override
	public boolean updateSong(Integer id, Song song) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Song entity = null;
        try {
            entity = em.find(Song.class, id);

            // set new values
            entity.setTitle(song.getTitle());
            entity.setArtist(song.getArtist());
            entity.setAlbum(song.getAlbum());
            entity.setReleased(song.getReleased());

            transaction.begin();
            em.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error updating Song.");
            e.printStackTrace();
        }
        finally {
            em.close();
        }
		return false;
	}

	@Override
	public boolean deleteSong(Integer id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Song song = null;
        try {
            song = em.find(Song.class, id);
            if (song != null) {
                System.out.println("Deleting: " + song.getId());
                transaction.begin();
                em.remove(song);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error removing song: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not remove entity: " + e.toString());
        } finally {
            em.close();
        }
        return false;
    }
}
