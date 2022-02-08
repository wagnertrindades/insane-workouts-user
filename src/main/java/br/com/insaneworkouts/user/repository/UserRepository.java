package br.com.insaneworkouts.user.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.insaneworkouts.user.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
