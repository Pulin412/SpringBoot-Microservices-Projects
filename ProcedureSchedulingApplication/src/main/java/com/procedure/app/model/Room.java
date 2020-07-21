/**
 * 
 */
package com.procedure.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Pulin
 *
 */
@Entity
public class Room {

	@Id
	private String id;
	
	@NotNull
	@NotBlank(message = "Name is mandatory")
	private String name;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
	private List<Study> study;
	
	/**
	 * 
	 */
	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param name
	 */
	public Room(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the study
	 */
	public List<Study> getStudy() {
		return study;
	}
	/**
	 * @param study the study to set
	 */
	public void setStudy(List<Study> study) {
		this.study = study;
	}
	
	
}
