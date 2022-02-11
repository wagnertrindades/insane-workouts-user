package br.com.insaneworkouts.user.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Optional;

import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
		.andExpect(jsonPath("$.name", is(equalTo("Xunda"))))
		.andExpect(jsonPath("$.password").doesNotExist());
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
	
	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	public void detail_Should_Returns200_When_SendUserID() throws Exception {
		User user = new User("test@email.com", "123456789", "Xunda");
		userRepository.save(user);
		
		URI uri = new URI("/api/user/" + user.getId());
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	public void detail_Should_ReturnsUserDetails_When_SendUserID() throws Exception {
		User user = new User("test@email.com", "123456789", "Xunda");
		userRepository.save(user);
		
		URI uri = new URI("/api/user/" + user.getId());
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(equalTo(Math.toIntExact(user.getId())))))
		.andExpect(jsonPath("$.email", is(equalTo(user.getEmail()))))
		.andExpect(jsonPath("$.name", is(equalTo(user.getName()))))
		.andExpect(jsonPath("$.password").doesNotExist());
	}
	
	@Test
	public void detail_Should_Returns404_When_UserNotExists() throws Exception {
		URI uri = new URI("/api/user/99999");
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(uri)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}

	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	public void update_Should_Returns200_When_UpdateUser() throws Exception {
		User user = new User("test@email.com", "123456789", "Xunda");
		userRepository.save(user);

		URI uri = new URI("/api/user/" + user.getId());
		String json = "{\"password\": \"987654321\", \"name\": \"Mussum\"}";

		mockMvc.perform(MockMvcRequestBuilders
						.put(uri)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	public void update_Should_ReturnsUserDataUpdated_When_UpdateUser() throws Exception {
		User user = new User("test@email.com", "123456789", "Xunda");
		userRepository.save(user);

		URI uri = new URI("/api/user/" + user.getId());
		String json = "{\"password\": \"987654321\", \"name\": \"Mussum\"}";

		mockMvc.perform(MockMvcRequestBuilders
						.put(uri)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is(equalTo(user.getEmail()))))
				.andExpect(jsonPath("$.name", is(equalTo("Mussum"))));
	}

	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	public void update_Should_UserDataUpdateDatabase_When_UpdateUser() throws Exception {
		User user = new User("test@email.com", "123456789", "Xunda");
		userRepository.save(user);

		URI uri = new URI("/api/user/" + user.getId());
		String json = "{\"password\": \"987654321\", \"name\": \"Mussum\"}";

		mockMvc.perform(MockMvcRequestBuilders
						.put(uri)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		User userUpdated = userRepository.findById(user.getId()).get();

		assertEquals("987654321", userUpdated.getPassword());
		assertEquals("Mussum", userUpdated.getName());
	}

	@Test
	public void update_Should_Returns404_When_UserNotExists() throws Exception {
		URI uri = new URI("/api/user/99999");
		String json = "{\"password\": \"987654321\", \"name\": \"Mussum\"}";

		mockMvc.perform(MockMvcRequestBuilders
						.put(uri)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void update_Should_Returns400_When_NotHasPassword() throws Exception {
		URI uri = new URI("/api/user/1");
		String json = "{\"name\": \"Mussum\"}";

		mockMvc.perform(MockMvcRequestBuilders
						.put(uri)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.violations").isArray())
				.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "password"))))
				.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "must not be null"))));
	}

	@Test
	public void update_Should_Returns400_When_NotHasName() throws Exception {
		URI uri = new URI("/api/user/1");
		String json = "{\"password\": \"987654321\"}";

		mockMvc.perform(MockMvcRequestBuilders
						.put(uri)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.violations").isArray())
				.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "name"))))
				.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "must not be null"))));
	}

	@Test
	public void update_Should_Returns400_When_PasswordIsBlank() throws Exception {
		URI uri = new URI("/api/user/1");
		String json = "{\"password\": \"\", \"name\": \"Mussum\"}";

		mockMvc.perform(MockMvcRequestBuilders
						.put(uri)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.violations").isArray())
				.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "password"))))
				.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "must not be empty"))));
	}

	@Test
	public void update_Should_Returns400_When_NameIsBlank() throws Exception {
		URI uri = new URI("/api/user/1");
		String json = "{\"password\": \"987654321\", \"name\": \"\"}";

		mockMvc.perform(MockMvcRequestBuilders
						.put(uri)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.violations").isArray())
				.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "name"))))
				.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "must not be empty"))));
	}

	@Test
	public void update_Should_Returns400_When_PasswordLengthLowerThen8() throws Exception {
		URI uri = new URI("/api/user/1");
		String json = "{\"password\": \"7654321\", \"name\": \"Mussum\"}";

		mockMvc.perform(MockMvcRequestBuilders
						.put(uri)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.violations").isArray())
				.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "password"))))
				.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "length must be 8 or more"))));
	}

	@Test
	public void update_Should_Returns400_When_NameLengthLowerThen3() throws Exception {
		URI uri = new URI("/api/user/1");
		String json = "{\"password\": \"987654321\", \"name\": \"Mu\"}";

		mockMvc.perform(MockMvcRequestBuilders
						.put(uri)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.violations").isArray())
				.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("fieldName", "name"))))
				.andExpect(jsonPath("$.violations", hasItem(IsMapContaining.hasEntry("message", "length must be 3 or more"))));
	}

	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	public void delete_Should_Returns204_When_SendUserID() throws Exception {
		User user = new User("test@email.com", "123456789", "Xunda");
		userRepository.save(user);

		URI uri = new URI("/api/user/" + user.getId());

		mockMvc.perform(MockMvcRequestBuilders
						.delete(uri)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	public void delete_Should_UserDataDeleteDatabase_When_DeleteUser() throws Exception {
		User user = new User("test@email.com", "123456789", "Xunda");
		userRepository.save(user);

		URI uri = new URI("/api/user/" + user.getId());

		mockMvc.perform(MockMvcRequestBuilders
						.delete(uri)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		Optional<User> userDeleted = userRepository.findById(user.getId());

		assertFalse(userDeleted.isPresent());
	}

	@Test
	public void delete_Should_Returns404_When_UserNotExists() throws Exception {
		URI uri = new URI("/api/user/99999");

		mockMvc.perform(MockMvcRequestBuilders
						.delete(uri)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
