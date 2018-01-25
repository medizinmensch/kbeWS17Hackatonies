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
import de.htwBerlin.ai.kbe.songsRx.beans.User;

public class SonglistDao implements ISonglistDao {

	@Inject
	private EntityManagerFactory emf;

	@Override
	public Integer createNewSongList(User user, String songlistName, boolean isPublic) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        Songlist newSonglist = new Songlist();
        newSonglist.setName(songlistName);
        newSonglist.setPublic(isPublic);
        newSonglist.setUser(user);

        try {
            transaction.begin();
            em.persist(newSonglist);
            transaction.commit();
            return newSonglist.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        finally {
            em.close();
        }
	}

    @Override
    public Integer createNewSongListWithPayload(User user, Songlist songlist) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            songlist.setUser(user);
            songlist.setId(null);

            transaction.begin();
            em.persist(songlist);
            transaction.commit();
            return songlist.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        finally {
            em.close();
        }
    }

	@Override
	public Songlist getSonglist(Integer songlistId, User user) {
		EntityManager em = emf.createEntityManager();
        Songlist entity = null;

        if (!userIsOwnerOfSonglist(user, songlistId))
            return null;

        try {
            entity = em.find(Songlist.class, songlistId);
        } finally {
            em.close();
        }
        return entity;
	}

	@Override
	public Collection<Songlist> getSonglistsOfUser(User currentUser) {
		EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Songlist> query = em.createQuery("SELECT sl FROM Songlist sl WHERE sl.user = :currentUser", Songlist.class);
            query.setParameter("currentUser", currentUser);
            Collection<Songlist> songlistsOfUser = query.getResultList();
            return songlistsOfUser;
        } finally {
            em.close();
        }
	}

	@Override
	public Collection<Songlist> getPublicSonglistsOfUser(User currentUser) {
		EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Songlist> query = em.createQuery("SELECT sl FROM Songlist sl WHERE sl.user = :currentUser AND sl.isPublic = 1", Songlist.class); //TODO "AND" richtig? TRUE ohne anführungszeichen?
            query.setParameter("currentUser", currentUser);
            return query.getResultList();
        } finally {
            em.close();
        }
	}

	@Override
	public boolean addSongsToSonglist(Integer songlistId, Collection<Song> songs) {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Songlist sl = em.find(Songlist.class, songlistId);
            
            sl.setSongs(songs);
            
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
	public boolean deleteSonglist(Integer songlistId, User user) {

	    if (!userIsOwnerOfSonglist(user, songlistId))
	        return false;

        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Songlist toDel = em.find(Songlist.class, songlistId);
            em.remove(toDel);
            transaction.commit();
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

	private boolean userIsOwnerOfSonglist(User user, Integer songListId) {
	    //return user.getSonglists().contains(getSonglist(songListId));

        for (Songlist songlist: user.getSonglists()) {
            if (songlist.getId().equals(songListId))
                return true;
        }
        return false;
    }

    public boolean songlistExists(Integer songlistId) {
        EntityManager em = emf.createEntityManager();
        try {
            Songlist toCheck = em.find(Songlist.class, songlistId);

            if (toCheck == null)
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error finding songlist: " + e.getMessage());
        } finally {
            em.close();
        }
        return true;
    }
}
