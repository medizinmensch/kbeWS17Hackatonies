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
	public Collection<Songlist> getAllSonglistsOfUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Song> getSonglistOfUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createNewSongListOfUser(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteSonglistOfUser(String userId) {
		// TODO Auto-generated method stub
		
	}

}
