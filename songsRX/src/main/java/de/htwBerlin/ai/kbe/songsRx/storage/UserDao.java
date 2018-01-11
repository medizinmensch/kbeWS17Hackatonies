package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;


import de.htwBerlin.ai.kbe.songsRx.beans.User;

public class UserDao implements IUserDao {

	@Inject
	EntityManagerFactory emf;
	
	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}user.getSonglists.setOwner(user); 
	user.getSonglists.setOwner(user); 
	@Overrideuser.getSonglists.setOwner(user); 
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
	public Integer addUser(User user) {
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
	}

}
