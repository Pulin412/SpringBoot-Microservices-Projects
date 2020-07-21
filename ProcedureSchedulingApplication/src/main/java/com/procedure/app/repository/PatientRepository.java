/**
 * 
 */
package com.procedure.app.repository;

import java.util.List;

import com.procedure.app.model.Doctor;
import com.procedure.app.model.Patient;
import com.procedure.app.model.Room;

/**
 * @author Pulin
 *
 *	Repository Interface for Patient/Room/Doctor related methods
 */
public interface PatientRepository {

	public Patient save(Patient patient);

	public List<Patient> getAllPatients();

	public Patient findById(String selectedId);

	public Room findRoomById(String selectedId);

	public Doctor findDoctorById(String selectedId);
}
