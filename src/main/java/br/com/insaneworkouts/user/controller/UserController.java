package br.com.insaneworkouts.user.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import br.com.insaneworkouts.user.controller.form.UserUpdateForm;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.insaneworkouts.user.controller.dto.UserDto;
import br.com.insaneworkouts.user.controller.form.UserForm;
import br.com.insaneworkouts.user.model.User;
import br.com.insaneworkouts.user.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserRepository userRepository;
	private final PasswordEncoder encoder;

	public UserController(UserRepository userRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid UserForm form, UriComponentsBuilder uriBuilder) {
		User user = form.converter();

		user.setPassword(encoder.encode(user.getPassword()));
		
		try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity
					.badRequest()
					.contentType(MediaType.APPLICATION_JSON)
					.body("{\"violations\": [{\"fieldName\": \"email\", \"message\": \"the email already exists\"}]}");
		}
		
		URI uri = uriBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(new UserDto(user));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> detail(@PathVariable Long id) {
		Optional<User> optional = userRepository.findById(id);

		if (optional.isPresent()) {
			return ResponseEntity.ok(new UserDto(optional.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserUpdateForm form) {
		Optional<User> optional = userRepository.findById(id);

		if (optional.isPresent()) {
			User user = optional.get();
			user.setName(form.getName());
			user.setPassword(encoder.encode(form.getPassword()));

			userRepository.save(user);

			return ResponseEntity.ok(new UserDto(user));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<User> optional = userRepository.findById(id);

		if (optional.isPresent()) {
			userRepository.deleteById(id);

			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}

}
