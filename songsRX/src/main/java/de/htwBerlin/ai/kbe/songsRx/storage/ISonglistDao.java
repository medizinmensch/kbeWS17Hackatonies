package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import de.htwBerlin.ai.kbe.songsRx.beans.Songlist;
import de.htwBerlin.ai.kbe.songsRx.beans.User;

public interface ISonglistDao {

	public Integer createNewSongList(User user, String songlistName, boolean isPublic);
	public Integer createNewSongListWithPayload(User user, Songlist songlist);
	public Songlist getSonglist(Integer songlistId);
	public Collection<Songlist> getSonglistsOfUser(String userId);	//public & private
	public Collection<Songlist> getPublicSonglistsOfUser(String userId); 
	public boolean addSongsToSonglist(Integer songlistId, Collection<Song> songs);
	public boolean deleteSongOfSonglist(Integer songlistId, Integer songId);
	// public boolean updateSongListOfUser(String userId, Integer songlistId, Collection<Song> songs); TODO 
	public boolean deleteSonglist(Integer songlistId);
	public boolean songlistIsPrivate(Integer songlistId);
}