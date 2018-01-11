package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;

public interface ISongDao {
	
	public Song getSong(Integer id);
	public Collection<Song> getAllSongs();
	public int addSong(Song song);
	public boolean deleteSong(Integer id);
}
