package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import de.htwBerlin.ai.kbe.songsRx.beans.Songlist;

public interface ISonglistDao {

	public Integer createNewSongList(String ownerUserId, String songlistName, boolean isPublic);
	public Songlist getSonglist(Integer songlistId);
	public Collection<Songlist> getSonglistsOfUser(String userId);	//public & private
	public Collection<Songlist> getPublicSonglistsOfUser(String userId); 
	public boolean addSongsToSonglist(Collection<Song> songs, Integer songlistId);
	public boolean deleteSongOfSonglist(Integer songlistId, Integer songId);
	// public boolean updateSongListOfUser(String userId, Integer songlistId, Collection<Song> songs); TODO 
	public boolean deleteSonglist(Integer songlistId);
	public boolean songlistIsPrivate(Integer songlistId);
}