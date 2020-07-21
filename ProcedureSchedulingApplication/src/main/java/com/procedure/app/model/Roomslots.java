package com.procedure.app.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Roomslots {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String slotId;
	@ElementCollection
	private List<String> availableSlots;
	@OneToOne(mappedBy = "slots")
	private Study study;

	/**
	 * @param slotId
	 * @param availableSlots
	 */
	public Roomslots(String slotId, List<String> availableSlots) {
		super();
		this.slotId = slotId;
		this.availableSlots = availableSlots;
	}

	/**
	 * 
	 */
	public Roomslots() {
		super();
	}

	/**
	 * @return the slotId
	 */
	public String getSlotId() {
		return slotId;
	}

	/**
	 * @param slotId the slotId to set
	 */
	public void setSlotId(String slotId) {
		this.slotId = slotId;
	}

	/**
	 * @return the availableSlots
	 */
	public List<String> getAvailableSlots() {
		return availableSlots;
	}

	/**
	 * @param availableSlots the availableSlots to set
	 */
	public void setAvailableSlots(List<String> availableSlots) {
		this.availableSlots = availableSlots;
	}
}
