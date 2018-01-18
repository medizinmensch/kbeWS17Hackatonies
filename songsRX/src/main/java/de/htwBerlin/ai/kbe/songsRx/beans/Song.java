package de.htwBerlin.ai.kbe.songsRx.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "song")
@Entity
@Table(name = "Song")
public class Song {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public static class Builder {
		//required parameter
		private Integer id;
		private String title;
		private String artist;
		private String album;
		private Integer released;

		public Builder(Integer id, String title, String artist, String album, Integer released) {
			this.id = id;
			this.title = title;
			this.artist = artist;
			this.album = album;
			this.released = released;
		}

		public Song build() {
			return new Song(this);
		}
	}

	private Song(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.artist = builder.artist;
		this.album = builder.album;
		this.released = builder.released;
	}




	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) { this.title = title;}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) { this.artist = artist;}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) { this.album = album;}

	public Integer getReleased() {
		return released;
	}

	public void setReleased(Integer released) { this.released = released;}

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

	@Override
	public String toString() {
		return "Song [id=" + id + ", Title=" + title + ", Artist=" + artist + ", Album=" + album
				+ ", Released=" + released + "]";
	}
}
