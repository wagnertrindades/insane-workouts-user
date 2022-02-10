package br.com.insaneworkouts.user.controller.dto;

import br.com.insaneworkouts.user.model.User;

public class UserDto {
	
	private Long id;
	private String email;
	private String name;
	
	public UserDto(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.name = user.getName();
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}
	
}
