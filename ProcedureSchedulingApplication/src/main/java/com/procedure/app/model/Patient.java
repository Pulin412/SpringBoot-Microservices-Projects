/**
 * 
 */
package com.procedure.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Pulin
 *
 */
@Entity
public class Patient {

	@Id
	private String id;
	@NotNull
	@NotBlank(message = "Name is mandatory")
	private String name;
	private String sex;
	private String dayOfBirth;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	private List<Study> study;
	
	
	/**
	 * 
	 */
	public Patient() {
		super();
	}
	
	/**
	 * @param id
	 * @param name
	 * @param sex
	 * @param dayOfBirth
	 */
	public Patient(String id, String name, String sex, String dayOfBirth) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.dayOfBirth = dayOfBirth;
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
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the dayOfBirth
	 */
	public String getDayOfBirth() {
		return dayOfBirth;
	}
	/**
	 * @param dayOfBirth the dayOfBirth to set
	 */
	public void setDayOfBirth(String dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
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
