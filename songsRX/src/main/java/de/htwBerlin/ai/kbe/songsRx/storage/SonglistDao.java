package de.htwBerlin.ai.kbe.songsRx.storage;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import de.htwBerlin.ai.kbe.songsRx.beans.Song;
import de.htwBerlin.ai.kbe.songsRx.beans.Songlist;

public class SonglistDao implements ISonglistDao {


	@Inject
	private EntityManagerFactory emf;

	@Override
	public Integer createNewSongListOfUser(String userId, Collection<Song> songs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<Song> getSonglistOfUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Songlist> getAllPublicSonglistsOfUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Songlist> getAllSonglistsOfUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateSongListOfUser(String userId, Integer songlistId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteSonglistOfUser(String userId, Integer songlistId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean songlistIsPrivate(Integer songlistId) {
		return false;
	}

}
