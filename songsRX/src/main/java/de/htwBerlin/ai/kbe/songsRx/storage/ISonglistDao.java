package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import de.htwBerlin.ai.kbe.songsRx.beans.Songlist;
import de.htwBerlin.ai.kbe.songsRx.beans.User;

public interface ISonglistDao {

	public Integer createNewSongListWithPayload(User user, Songlist songlist);
	public Songlist getSonglist(Integer songlistId, User user);
	public Collection<Songlist> getSonglistsOfUser(User currentUser);	//public & private
	public Collection<Songlist> getPublicSonglistsOfUser(User currentUser);
	public boolean addSongsToSonglist(Integer songlistId, Collection<Song> songs);
	public boolean deleteSonglist(Integer songlistId, User user);
	public boolean songlistIsPrivate(Integer songlistId);
    boolean songlistExists(Integer songlistId);

    // not yet implemented
	public Integer createNewSongList(User user, String songlistName, boolean isPublic);
	public boolean deleteSongOfSonglist(Integer songlistId, Integer songId);
	// public boolean updateSongListOfUser(String userId, Integer songlistId, Collection<Song> songs); TODO
}