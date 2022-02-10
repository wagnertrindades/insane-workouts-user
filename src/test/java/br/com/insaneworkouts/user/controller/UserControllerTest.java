package br.com.insaneworkouts.user.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.insaneworkouts.user.model.User;
import br.com.insaneworkouts.user.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepository;

	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	public void create_Should_Returns201_When_CreateUser() throws Exception {
		URI uri = new URI("/api/user");
		String json = "{\"email\": \"test@email.com\", \"password\": \"123456789\", \"name\": \"Xunda\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	public void create_Should_ReturnsUserDetails_When_CreateUser() throws Exception {
		URI uri = new URI("/api/user");
		String json = "{\"email\": \"test@email.com\", \"password\": \"123456789\", \"name\": \"Xunda\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").isNumber())
		.andExpect(jsonPath("$.email", is(equalTo("test@email.com"))))
		.andExpect(jsonPath("$.password", is(equalTo("123456789"))))
		.andExpect(jsonPath("$.name", is(equalTo("Xunda"))));
	}
	
	@Test
	public void create_Should_Returns400_When_NotHasEmail() throws Exception {
		URI uri = new URI("/api/user");
		String json = "{\"password\": \"123456789\", \"name\": \"Xunda\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.violations").isArray())
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "email"))))
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "must not be null"))));
	}
	
	@Test
	public void create_Should_Returns400_When_NotHasPassword() throws Exception {
		URI uri = new URI("/api/user");
		String json = "{\"email\": \"test@email.com\", \"name\": \"Xunda\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.violations").isArray())
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "password"))))
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "must not be null"))));
	}
	
	@Test
	public void create_Should_Returns400_When_NotHasName() throws Exception {
		URI uri = new URI("/api/user");
		String json = "{\"email\": \"test@email.com\", \"password\": \"123456789\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.violations").isArray())
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "name"))))
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "must not be null"))));
	}
	
	@Test
	public void create_Should_Returns400_When_EmailIsBlank() throws Exception {
		URI uri = new URI("/api/user");
		String json = "{\"email\": \"\", \"password\": \"123456789\", \"name\": \"Xunda\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.violations").isArray())
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "email"))))
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "must not be empty"))));
	}
	
	@Test
	public void create_Should_Returns400_When_PasswordIsBlank() throws Exception {
		URI uri = new URI("/api/user");
		String json = "{\"email\": \"test@email.com\", \"password\": \"\", \"name\": \"Xunda\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.violations").isArray())
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "password"))))
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "must not be empty"))));
	}
	
	@Test
	public void create_Should_Returns400_When_NameIsBlank() throws Exception {
		URI uri = new URI("/api/user");
		String json = "{\"email\": \"test@email.com\", \"password\": \"123456789\", \"name\": \"\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.violations").isArray())
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "name"))))
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "must not be empty"))));
	}
	
	@Test
	public void create_Should_Returns400_When_EmailNotHasEmailFormat() throws Exception {
		URI uri = new URI("/api/user");
		String json = "{\"email\": \"kdjfakjdlafkj\", \"password\": \"123456789\", \"name\": \"Xunda\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.violations").isArray())
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "email"))))
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "must be a well-formed email address"))));
	}
	
	@Test
	public void create_Should_Returns400_When_PasswordLengthLowerThen8() throws Exception {
		URI uri = new URI("/api/user");
		String json = "{\"email\": \"test@email.com\", \"password\": \"1234567\", \"name\": \"Xunda\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.violations").isArray())
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "password"))))
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "length must be 8 or more"))));
	}
	
	@Test
	public void create_Should_Returns400_When_NameLengthLowerThen3() throws Exception {
		URI uri = new URI("/api/user");
		String json = "{\"email\": \"test@email.com\", \"password\": \"123456789\", \"name\": \"Xu\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.violations").isArray())
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "name"))))
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "length must be 3 or more"))));
	}
	
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	public void create_Should_Returns400_When_EmailExists() throws Exception {
		User user = new User("test@email.com", "123456789", "Xunda");
		userRepository.save(user);
		
		URI uri = new URI("/api/user");
		String json = "{\"email\": \"test@email.com\", \"password\": \"123456789\", \"name\": \"Xunda\"}";
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.violations").isArray())
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "email"))))
		.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "the email already exists"))));
	}

}
