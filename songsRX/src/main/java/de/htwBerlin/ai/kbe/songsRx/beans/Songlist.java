package de.htwBerlin.ai.kbe.songsRx.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "songlist")
@Entity
@Table(name = "Songlist")
public class Songlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

    private boolean isPublic;
	
	@ManyToMany(fetch = FetchType.EAGER) //cascade = CascadeType.PERSIST
	private Collection<Song> songs;

	@ManyToOne
	@JoinColumn(name="user_Id")
	private User user;

	public Songlist() {}

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

	public Collection<Song> getSongs() {
		return songs;
	}

	public void setSongs(Collection<Song> songs) {
		this.songs = songs;
	}

	@JsonIgnore
	@XmlTransient
	public User getUser() {
		return user;
	}

	@JsonIgnore
	public void setUser(User user) {
		this.user = user;
	}
	
	public void addSong(Song song) {
		this.songs.add(song);
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
