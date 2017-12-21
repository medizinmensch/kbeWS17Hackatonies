package de.htwBerlin.ai.kbe.songsRx.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class User {
	
	private Integer id;
	private String userId;
	private String lastName;
	private String firstName;
	//private List<Songlist> songlist;
	private String token;
	
	public User(Integer id, String userId, String lastName, String firstName) {
		super();
		this.id = id;
		this.userId = userId;
		this.lastName = lastName;
		this.firstName = firstName;
	}
	
	
	public User() {}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

}