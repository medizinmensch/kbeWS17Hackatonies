package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.songsRx.beans.User;

public interface IUserDao {

	public Integer createUser(User user);
	public User getUser(String userId);
	public Collection<User> getAllUsers();
	public boolean updateUser(String userId, User updatedUser);
	public boolean deleteUser(String userId);
	public boolean userExists(String userId);
	public boolean userIsInDatabase(String userId);
	Integer getIdFromUserId(String userId);
}
