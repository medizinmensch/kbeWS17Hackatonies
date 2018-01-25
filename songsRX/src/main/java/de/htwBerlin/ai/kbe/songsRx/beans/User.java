package de.htwBerlin.ai.kbe.songsRx.beans;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
@Entity
@Table(name = "User")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String userId;
	private String lastName;
	private String firstName;

	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
	private Collection<Songlist> songlists;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public Collection<Songlist> getSonglists() {
		return songlists;
	}

	public void setSonglists(Collection<Songlist> songlists) {
		this.songlists = songlists;
	}
	
	
}