package br.com.insaneworkouts.user.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.util.Assert;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class UserTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	private User user;
	
	@BeforeAll
	public void init() {
		user = new User("xubaca@email.com", "123456", "Xubaca");
	}
	
	@Test
	public void shouldHasId() {
		User savedUser = this.entityManager.persistAndFlush(user);
		
		Assert.notNull(savedUser.getId(), "The id must not be null");
	}
	
	@Test
	public void shouldHasName() {
		String userName = user.getName();
		
		Assert.notNull(userName, "The name must not be null");
	}
	
	@Test
	public void shouldHasEmail() {		
		String userEmail = user.getEmail();
		
		Assert.notNull(userEmail, "The email must not be null");
	}
	
	@Test
	public void shouldHasPassword() {
		String userPassword = user.getPassword();
		
		Assert.notNull(userPassword, "The password must not be null");
	}
	
	@Test
	public void shouldSetName() {
		user.setName("Xunda");
		String userName = user.getName();
		
		assertEquals("Xunda", userName);
	}
	
	@Test
	public void shouldSetPassword() {
		user.setPassword("123");
		String userPassword = user.getPassword();
		
		assertEquals("123", userPassword);
	}

}
