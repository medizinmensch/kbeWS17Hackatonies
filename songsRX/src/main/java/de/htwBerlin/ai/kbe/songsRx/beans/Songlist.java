package de.htwBerlin.ai.kbe.songsRx.beans;

import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "songlist")
@Entity
public class Songlist {

	boolean isPublic;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	
	public Songlist() {}
	
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<Song> songs;

	@JoinColumn(name="userId")
	@ManyToOne
	private User user;

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void addSong(Song song) {
		this.songs.add(song);
	}
	
	
	
}
