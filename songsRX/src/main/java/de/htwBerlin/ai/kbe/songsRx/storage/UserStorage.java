package de.htwBerlin.ai.kbe.songsRx.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htwBerlin.ai.kbe.songsRx.beans.User;

public class UserStorage {

	private static Map<Integer,User> storage;
	private static UserStorage instance = null;
	private static AtomicInteger currentId;
	
	private UserStorage() {
		storage = new HashMap<Integer,User>();

		String jsonFile = "user.json"; //TODO
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(jsonFile);

		List<User> userList;

		try {
			userList = objectMapper.readValue(input, new TypeReference<List<User>>() {
			});

			for (User s: userList) {
				storage.put(s.getId(), s);
			}

		} catch (IOException e) {
			System.out.println("could not read json file.");
		}
		currentId = new AtomicInteger(getMaxId());
	}
	
	public synchronized static UserStorage getInstance() {
		if (instance == null) {
			instance = new UserStorage();
		}
		return instance;
	}
	
	public User getUser(Integer id) {
		return storage.get(id);
	}
	
	public Collection<User> getAllUsers() {
		return storage.values();
	}
	
	public Integer addUser(User user) {
		user.setId(currentId.incrementAndGet());
		storage.put(user.getId(), user);
		return user.getId();
	}
	
	// returns true (success), when user exists in map and was updated
	// returns false, when contact does not exist in map
	public boolean updateUser(User newUser, int newId) {
		User oldUser = storage.get(newId);
		if (oldUser != null) {
			newUser.setId(newId);
			return storage.replace(newId, oldUser, newUser);
		}
		// id does not exist
		else
			return false;

	}
	
	// returns deleted user. why return? just give true for success
	public boolean deleteUser(Integer id) {
		User deletedUser = storage.remove(id);
		if (deletedUser != null)
			return true;
		return false;
	}

	
	private Integer getMaxId() {
		Integer max = 0;
		Iterator it = storage.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry user = (Map.Entry)it.next();
			if ((Integer) user.getKey() > max) {
				max = (Integer) user.getKey();
			}
		}
		return max;
	}
	
}
