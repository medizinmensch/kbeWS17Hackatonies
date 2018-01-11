package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import de.htwBerlin.ai.kbe.songsRx.beans.Songlist;

public interface ISonglistDao {
	public Collection<Songlist> getAllSonglistsOfUser(String userId);
	public Collection<Song> getSonglistOfUser(String userId);
	public int createNewSongListOfUser(String userId);
	public void deleteSonglistOfUser(String userId);
}
