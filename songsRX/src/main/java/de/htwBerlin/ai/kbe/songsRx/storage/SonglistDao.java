package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;
import java.util.List;

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

	@Inject
    private IUserDao userDao;

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
            TypedQuery<Songlist> query = em.createQuery("SELECT sl FROM Songlist sl WHERE sl.user = :currentUser", Songlist.class);
            query.setParameter("currentUser", userDao.getUser(userId));
            List<Songlist> bla = query.getResultList();
            return bla;
        } finally {
            em.close();
        }
	}

	@Override
	public Collection<Songlist> getPublicSonglistsOfUser(String userId) {
		EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Songlist> query = em.createQuery("SELECT songlists FROM User u WHERE u.userId = :userId AND Songlist.isPublic = TRUE", Songlist.class); //TODO "AND" richtig? TRUE ohne anführungszeichen?
            query.setParameter("userId", userId);
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
