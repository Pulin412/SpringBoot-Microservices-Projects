package com.procedure.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Study {

	@Id
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", nullable = false)
	private Room room;

	@NotEmpty(message = "Description is mandatory")
	private String description;
	private String status;
	private String hrsStartTime;
	private String hrsEndTime;

	private String selectedId;
	private String selectedDoctorId;
	private String selectedRoomId;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "slots_id", referencedColumnName = "id")
	private Slots slots;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctorslots_id", referencedColumnName = "id")
	private Doctorslots doctorslots;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roomslots_id", referencedColumnName = "id")
	private Roomslots roomslots;

	/**
	 * 
	 */
	public Study() {
		super();
	}

	/**
	 * @param id
	 * @param description
	 * @param status
	 * @param plannedStartTime
	 * @param estimatedEndTime
	 */
	public Study(String id, String description, String status) {
		super();
		this.id = id;
		this.description = description;
		this.status = status;
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
	 * @return the patient
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * @param patient the patient to set
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * @return the doctor
	 */
	public Doctor getDoctor() {
		return doctor;
	}

	/**
	 * @param doctor the doctor to set
	 */
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the selectedId
	 */
	public String getSelectedId() {
		return selectedId;
	}

	/**
	 * @param selectedId the selectedId to set
	 */
	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	/**
	 * @return the hrsStartTime
	 */
	public String getHrsStartTime() {
		return hrsStartTime;
	}

	/**
	 * @param hrsStartTime the hrsStartTime to set
	 */
	public void setHrsStartTime(String hrsStartTime) {
		this.hrsStartTime = hrsStartTime;
	}

	/**
	 * @return the hrsEndTime
	 */
	public String getHrsEndTime() {
		return hrsEndTime;
	}

	/**
	 * @param hrsEndTime the hrsEndTime to set
	 */
	public void setHrsEndTime(String hrsEndTime) {
		this.hrsEndTime = hrsEndTime;
	}

	/**
	 * @return the selectedDoctorId
	 */
	public String getSelectedDoctorId() {
		return selectedDoctorId;
	}

	/**
	 * @param selectedDoctorId the selectedDoctorId to set
	 */
	public void setSelectedDoctorId(String selectedDoctorId) {
		this.selectedDoctorId = selectedDoctorId;
	}

	/**
	 * @return the selectedRoomId
	 */
	public String getSelectedRoomId() {
		return selectedRoomId;
	}

	/**
	 * @param selectedRoomId the selectedRoomId to set
	 */
	public void setSelectedRoomId(String selectedRoomId) {
		this.selectedRoomId = selectedRoomId;
	}

	/**
	 * @return the slots
	 */
	public Slots getSlots() {
		return slots;
	}

	/**
	 * @param slots the slots to set
	 */
	public void setSlots(Slots slots) {
		this.slots = slots;
	}

	/**
	 * @return the doctorslots
	 */
	public Doctorslots getDoctorslots() {
		return doctorslots;
	}

	/**
	 * @param doctorslots the doctorslots to set
	 */
	public void setDoctorslots(Doctorslots doctorslots) {
		this.doctorslots = doctorslots;
	}

	/**
	 * @return the roomslots
	 */
	public Roomslots getRoomslots() {
		return roomslots;
	}

	/**
	 * @param roomslots the roomslots to set
	 */
	public void setRoomslots(Roomslots roomslots) {
		this.roomslots = roomslots;
	}

}
