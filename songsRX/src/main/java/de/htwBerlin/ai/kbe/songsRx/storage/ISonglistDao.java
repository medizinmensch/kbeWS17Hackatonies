package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import de.htwBerlin.ai.kbe.songsRx.beans.Songlist;

public interface ISonglistDao {

	public Integer createNewSongListOfUser(String userId);
	public Collection<Song> getSonglistOfUser(String userId);
	public Collection<Songlist> getAllSonglistsOfUser(String userId);	//public & private
	public Collection<Songlist> getAllPublicSonglistsOfUser(String userId);
	public boolean updateSongListOfUser(String userId, Integer songlistId);
	public boolean deleteSonglistOfUser(String userId, Integer songlistId);
}
