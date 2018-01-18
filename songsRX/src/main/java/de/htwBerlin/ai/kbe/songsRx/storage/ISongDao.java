package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;

public interface ISongDao {

	public Integer createSong(Song song);
	public Song getSong(Integer id);
	public Collection<Song> getAllSongs();
	public boolean updateSong();
	public boolean deleteSong(Integer id);
}
