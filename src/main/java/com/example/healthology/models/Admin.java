package com.example.healthology.models;

import javax.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(columnDefinition = "TEXT")
	private String qualifications;

	@OneToOne
	@JoinColumn (name = "user_id", referencedColumnName = "id")
	private User user_id;


	public Admin(String qualifications, User user_id) {
		this.qualifications = qualifications;
		this.user_id = user_id;
	}

	public Admin () {};

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQualifications() {
		return qualifications;
	}

	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}

	public User getUser_id() {
		return user_id;
	}

	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}
}
