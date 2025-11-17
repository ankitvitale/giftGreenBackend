package com.giftGreenEcom.DTO;

import com.giftGreenEcom.Entity.Admin;
import com.giftGreenEcom.Entity.User;

public class JwtResponse {

	private Admin admin;

	private User user;
	private String jwtToken;

	public JwtResponse(Admin admin, User user, String jwtToken) {
		this.admin = admin;
		this.user = user;
		this.jwtToken = jwtToken;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
}
