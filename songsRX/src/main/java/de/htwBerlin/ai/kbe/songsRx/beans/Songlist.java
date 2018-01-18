package de.htwBerlin.ai.kbe.songsRx.beans;

import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "songlist")
public class Songlist {

	enum Listing {
		isPrivate, isPublic
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	Listing listing;

	private String name;

	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<Song> songs;

	@JoinColumn(name="userId")
	private User user;
}
