package com.example.group07Final.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {
	@Id
	@Column(name = "login")
    private String login;
	
	@Column(name = "password")
    private String password;
	
	public Admin() {}
	
	public Admin(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isLoginMatch(String login) {
		return this.login.equals(login);
	}

	public boolean isPasswordMatch(String password) {
		return this.password.equals(password);
	}
}
