package net.cityxen.cxnbbs.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.cityxen.cxnbbs.domain.User;

public interface UserRepository extends MongoRepository<User, String>  {
	User findByUsername(String username);
}
