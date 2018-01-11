package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.songsRx.beans.User;

public interface IUserDao {
	public User getUser(String userId);
	public Collection<User> getAllUsers();
	public Integer addUser(User user);
	public boolean deleteUser(Integer id);
	public boolean userExists(String userId);
}
