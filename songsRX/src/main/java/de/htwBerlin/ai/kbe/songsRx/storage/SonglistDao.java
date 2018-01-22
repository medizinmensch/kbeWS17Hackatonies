package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import de.htwBerlin.ai.kbe.songsRx.beans.Songlist;

public class SonglistDao implements ISonglistDao {


	@Inject
	private EntityManagerFactory emf;

	@Override
	public Integer createNewSongList(String ownerUserId, String songlistName, boolean isPublic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Songlist getSonglist(Integer songlistId) {
		EntityManager em = emf.createEntityManager();
        Songlist entity = null;
        try {
            entity = em.find(Songlist.class, songlistId);
        } finally {
            em.close();
        }
        return entity;
	}

	@Override
	public Collection<Songlist> getSonglistsOfUser(String userId) {
		EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Songlist> query = em.createQuery("SELECT sl FROM User u WHERE u.userId = :userId", Songlist.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
	}

	@Override
	public Collection<Songlist> getPublicSonglistsOfUser(String userId) {
		EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Songlist> query = em.createQuery("SELECT sl FROM User u WHERE u.userId = :userId AND isPublic = TRUE", Songlist.class); //TODO "AND" richtig? TRUE ohne anführungszeichen?
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
	}

	@Override
	public boolean addSongsToSonglist(Collection<Song> songs, Integer songlistId) {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Songlist sl = em.find(Songlist.class, songlistId);
            
            for (Song song:sl.getSongs()) {
                sl.addSong(song);
            }
            
            em.persist(sl); //TODO: persist immer wenn ich was update? Oder nur wenn ich etwas neues eintragen möchte?
            
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding Songs to songlist: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
	}

	@Override
	public boolean deleteSongOfSonglist(Integer songlistId, Integer songId) {
		EntityManager em = emf.createEntityManager();
		
		try {
			Song toDel = em.find(Song.class, songId);
			em.remove(toDel); //TODO: richtig so?
			return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error deleting song: " + e.getMessage());
        } finally {
            em.close();
        }
		return false;
	}

	@Override
	public boolean deleteSonglist(Integer songlistId) {
		EntityManager em = emf.createEntityManager();
		
		try {
			Songlist toDel = em.find(Songlist.class, songlistId);
			em.remove(toDel); //TODO: richtig so?
			return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error deleting songlist: " + e.getMessage());
        } finally {
            em.close();
        }
		return false;
	}

	@Override
	public boolean songlistIsPrivate(Integer songlistId) {
		EntityManager em = emf.createEntityManager();
		
		try {
			Songlist toCheck = em.find(Songlist.class, songlistId);
			
			return !toCheck.isPublic();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error deleting songlist: " + e.getMessage());
        } finally {
            em.close();
        }
		return false;
	}
}
