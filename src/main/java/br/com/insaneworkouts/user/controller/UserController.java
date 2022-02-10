package br.com.insaneworkouts.user.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.insaneworkouts.user.controller.dto.UserDto;
import br.com.insaneworkouts.user.controller.form.UserForm;
import br.com.insaneworkouts.user.model.User;
import br.com.insaneworkouts.user.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid UserForm form, UriComponentsBuilder uriBuilder) {
		User user = form.converter();
		
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
}
