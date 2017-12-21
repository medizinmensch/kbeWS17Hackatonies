package de.htwBerlin.ai.kbe.songsRx.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "songlist")
public class Songlist {
	
	enum Listing{
		privateListing,
		publicListing
	}

	Listing listing;
	private String name;
	private List<Song> songs;
}
