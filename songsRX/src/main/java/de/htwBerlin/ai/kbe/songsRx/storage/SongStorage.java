package de.htwBerlin.ai.kbe.songsRx.storage;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SongStorage {
	
	private static Map<Integer,Song> storage;
	private static SongStorage instance = null;
	private static AtomicInteger currentId;
	
	private SongStorage() {
		storage = new HashMap<Integer,Song>();

		String jsonFile = "songs.json";
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(jsonFile);

		List<Song> songList;

		try {
			songList = objectMapper.readValue(input, new TypeReference<List<Song>>() {
			});

			for (Song s: songList) {
				storage.put(s.getId(), s);
			}

		} catch (IOException e) {
			System.out.println("could not read json file.");
		}
		currentId = new AtomicInteger(getMaxId());


	}
	
	public synchronized static SongStorage getInstance() {
		if (instance == null) {
			instance = new SongStorage();
		}
		return instance;
	}
	
	public Song getSong(Integer id) {
		return storage.get(id);
	}
	
	public Collection<Song> getAllSongs() {
		return storage.values();
	}
	
	public Integer addSong(Song song) {
		song.setId(currentId.incrementAndGet());
		storage.put(song.getId(), song);
		return song.getId();
	}
	
	// returns true (success), when song exists in map and was updated
	// returns false, when contact does not exist in map
	public boolean updateSong(Song newSong, int newId) {
		Song oldSong = storage.get(newId);
		if (oldSong != null) {
			newSong.setId(newId);
			return storage.replace(newId, oldSong, newSong);
		}
		// id does not exist
		else
			return false;


	}
	
	// returns deleted song. why return? just give true for success
	public boolean deleteSong(Integer id) {
		Song deletedSong = storage.remove(id);
		if (deletedSong != null)
			return true;
		return false;
	}

	
	private Integer getMaxId() {
		Integer max = 0;
		Iterator it = storage.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry song = (Map.Entry)it.next();
			if ((Integer) song.getKey() > max) {
				max = (Integer) song.getKey();
			}
		}
		return max;
	}
}
