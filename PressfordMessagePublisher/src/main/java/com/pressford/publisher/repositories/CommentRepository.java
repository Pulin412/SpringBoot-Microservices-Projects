/**
 *
 */
package com.pressford.publisher.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pressford.publisher.entities.Comment;

/**
 * @author Pulin
 *
 *         Comment Repository using the Crud Repository of spring framework to
 *         interact with the database.
 *
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

	/**
	 * @param messageId
	 * @return List of Comment
	 *
	 *         Fetch Comments on the basis of message Id
	 */
	List<Comment> findByMessageId(Long messageId);

	/**
	 * @param id
	 * @param messageId
	 * @return Optional Comment
	 *
	 *         Fetch Comment on the basis of id and message Id.
	 */
	Optional<Comment> findByIdAndMessageId(Long id, Long messageId);

}
