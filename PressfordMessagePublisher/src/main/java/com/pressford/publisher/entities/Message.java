package com.pressford.publisher.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.expression.ParseException;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Pulin
 *
 *         Message Entity used to store Message related fields.
 *
 */
@Entity
public class Message {

	private final static String datePattern = "yyyy-MM-dd";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotBlank(message = "Title is mandatory")
	@Size(min = 1, max = 254)
	private String title;

	@Lob
	@Column(name = "body", columnDefinition = "CLOB")
	@NotBlank(message = "Body is mandatory")
	private String body;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateCreated;
	@Size(min = 0, max = 254)
	@NotBlank(message = "Creator is mandatory")
	private String creator;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "message_users", joinColumns = { @JoinColumn(name = "message_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	private Set<User> likedByEmployees = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "message")
	private Set<Comment> comments = new HashSet<>(0);

	/**
	 * Default constructor
	 */
	public Message() {
	}

	/**
	 * @param id
	 * @param name
	 * @param email
	 * @param title
	 * @param body
	 * @param dateCreated
	 * @param creator
	 *
	 *            Parameterized Constructor
	 */
	public Message(String title, String body, Date dateCreated, String creator) {
		super();
		this.title = title;
		this.body = body;
		this.dateCreated = dateCreated;
		this.creator = creator;
	}

	@Override
	public String toString() {
		return "Message{" + "id=" + id + '}';
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the dateCreated
	 */
	public String getDateCreatedStr() {
		if (getDateCreated() != null) {
			SimpleDateFormat format1 = new SimpleDateFormat(datePattern);
			String inActiveDate = null;
			try {
				inActiveDate = format1.format(getDateCreated());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			return inActiveDate;
		}

		return null;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	/**
	 * @param dateCreated
	 *            the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the likedByEmployees
	 */
	public Set<User> getLikedByEmployees() {
		return likedByEmployees;
	}

	/**
	 * @param likedByEmployees
	 *            the likedByEmployees to set
	 */
	public void setLikedByEmployees(Set<User> likedByEmployees) {
		this.likedByEmployees = likedByEmployees;
	}

	/**
	 * @return the comments
	 */
	public Set<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

}