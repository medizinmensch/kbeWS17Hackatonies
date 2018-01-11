package de.htwBerlin.ai.kbe.songsRx.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "songlist")
public class Songlist {

	enum Listing {
		privateListing, publicListing
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	Listing listing;
	private String name;

	@ManyToOne
	@JoinColumn(name = "song_id") //?? TODO noch richtig stellen
	private List<Song> songs;
}
