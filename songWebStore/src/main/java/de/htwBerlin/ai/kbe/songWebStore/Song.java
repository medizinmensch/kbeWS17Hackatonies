package de.htwBerlin.ai.kbe.songWebStore;

public class Song {

	private Integer id;
	private String title;
	private String artist;
	private String album;
	private Integer released;

	public Song() {
	}

	public Song(Integer id, String title, String artist, String album, Integer released) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.released = released;
	}

	public Integer getId() {
		return id;
	}

	void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public String getAlbum() {
		return album;
	}

	public Integer getReleased() {
		return released;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Song)) {
			return false;
		}
		Song checkSong = (Song) other;

		return this.getArtist().equals(checkSong.getArtist())
				&& this.getAlbum().equals(checkSong.getAlbum())
				&& this.getTitle().equals(checkSong.getTitle())
				&& this.getReleased().equals(checkSong.getReleased())
				&& this.getId().equals(checkSong.getId());
	}
}
