package com.pressford.publisher.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pressford.publisher.entities.Message;

/**
 * @author Pulin
 *
 *         Message Repository using the Crud Repository of spring framework to
 *         interact with the database.
 *
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}