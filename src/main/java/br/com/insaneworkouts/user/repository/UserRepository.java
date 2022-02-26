package br.com.insaneworkouts.user.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.insaneworkouts.user.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>{

    Optional<User> findByEmail(String email);
}
