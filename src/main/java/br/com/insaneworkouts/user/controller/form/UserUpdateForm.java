package br.com.insaneworkouts.user.controller.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserUpdateForm {

    @NotNull @NotEmpty @Length(min = 8, message = "length must be 8 or more")
    private String password;

    @NotNull @NotEmpty @Length(min = 3, message = "length must be 3 or more")
    private String name;

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

}