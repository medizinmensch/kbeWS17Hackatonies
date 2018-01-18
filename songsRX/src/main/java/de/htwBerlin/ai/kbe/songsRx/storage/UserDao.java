package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import de.htwBerlin.ai.kbe.songsRx.beans.User;
import de.htwBerlin.ai.kbe.songsRx.beans.Songlist;

public class UserDao implements IUserDao {

	@Inject
	EntityManagerFactory emf;

	@Override
	public Integer createUser(User user) {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            for (Songlist sl:user.getSonglists()) {
                sl.setUser(user);
            }
            
            em.persist(user);
            transaction.commit();
            return user.getId();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding user: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<User> getAllUsers() {
		EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT c FROM Contact c", User.class);
            return query.getResultList();
        } finally {
            em.close();
        }
	}

	@Override
	public boolean updateUser(String userId, User updatedUser) {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class);
            query.setParameter("userId", userId);
            User userToUpdate = query.getSingleResult();
            userToUpdate.setFirstName(updatedUser.getFirstName());
            userToUpdate.setLastName(updatedUser.getLastName());
            
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding contact: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
	}

	@Override
	public boolean deleteUser(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean userExists(String userId) {
        return false;
	}
	
	
	
	
	/*user.getSonglists.setOwner(user); 
	user.getSonglists.setOwner(user); 
	@Override user.getSonglists.setOwner(user); 
	public Collection<User> getAllUsers() {
		EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT c FROM Contact c", User.class);
            return query.getResultList();
        } finally {
            em.close();
        }
	}

	@Override
	public Integer createUser(User user) {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            // setContact an address ist wichtig! Sonst:
            // java.sql.SQLIntegrityConstraintViolationException: 
            // Column 'contact_id' cannot be null
            user.getAddress().setContact(contact); //TODO: noch übertragen
            user.getSonglists.setOwner(user); //so ungefähr?
            em.persist(user);
            transaction.commit();
            return user.getId();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding contact: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
	}

	@Override
	public boolean deleteUser(Integer id) {
		EntityManager em = emf.createEntityManager();
		return false;
	}

	@Override
	public boolean userExists(String userId) {
		EntityManager em = emf.createEntityManager();
		return false;
	}*/

}
