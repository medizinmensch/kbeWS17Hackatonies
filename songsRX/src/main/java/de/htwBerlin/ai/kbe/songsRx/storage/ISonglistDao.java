package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import de.htwBerlin.ai.kbe.songsRx.beans.Songlist;

public interface ISonglistDao {

	public Integer createNewSongListOfUser(String userId, Collection<Song> songs);
	public Collection<Song> getSonglistOfUser(Integer songlistId, String userId);
	public Collection<Songlist> getAllSonglistsOfUser(String userId);	//public & private
	public Collection<Songlist> getAllPublicSonglistsOfUser(String userId);
	public boolean updateSongListOfUser(String userId, Integer songlistId);
	public boolean deleteSonglistOfUser(String userId, Integer songlistId);
	public boolean songlistIsPrivate(Integer songlistId);
}
