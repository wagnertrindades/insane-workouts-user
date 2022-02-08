package br.com.insaneworkouts.user.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.insaneworkouts.user.model.User;

public class UserForm {

	@NotNull @NotEmpty @Length(min = 10)
	private String email;
	
	@NotNull @NotEmpty @Length(min = 8)
	private String password;
	
	@NotNull @NotEmpty @Length(min = 3)
	private String name;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User converter() {
		return new User(email, password, name);
	}
	
}
