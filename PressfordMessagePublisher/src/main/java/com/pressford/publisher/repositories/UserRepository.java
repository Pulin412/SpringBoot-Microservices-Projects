package com.pressford.publisher.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pressford.publisher.entities.User;

/**
 * @author Pulin
 *
 *         Usern Repository using the Crud Repository of spring framework to
 *         interact with the database.
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * @param userId
	 * @return User object
	 *
	 *         Get the user for the given userId
	 */
	User findByUserId(String userId);

}
