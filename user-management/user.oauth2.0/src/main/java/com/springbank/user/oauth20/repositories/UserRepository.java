package com.springbank.user.oauth20.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.springbank.user.core.models.User;

public interface UserRepository extends MongoRepository <User, String>{
	
	@Query("{'account.username' : ?0}")
	Optional<User> findByUsername(String username);

}
