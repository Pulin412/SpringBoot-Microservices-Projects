/**
 * 
 */
package com.procedure.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.procedure.app.model.Doctor;
import com.procedure.app.model.Patient;
import com.procedure.app.model.Room;
import com.procedure.app.model.Study;
import com.procedure.app.repository.PatientRepository;

/**
 * @author Pulin
 *
 * Service class for Patient/Room/Doctor
 */
@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;
	
	public Patient save(Patient patient) {
		return patientRepository.save(patient);
	}

	public List<Patient> getAllPatients() {
		return patientRepository.getAllPatients();
	}

	public Patient findById(String selectedId) {
		return patientRepository.findById(selectedId);
	}

	public Doctor findDoctorById(String doctorId) {
		return patientRepository.findDoctorById(doctorId);
	}

	public Room findRoomById(String roomId) {
		return patientRepository.findRoomById(roomId);
	}
	
	/**
	 * @param allStudies
	 * @return String
	 * 	
	 * Method to auto generate the patient Id
	 */
	public String setPatientId(List<Patient> patients) {

		if (patients.size() == 0) {
			return "p1";
		} else {
			Patient pat = patients.get(patients.size() - 1);
			String lastId = pat.getId();
			String returnId = "p" + String.valueOf(Integer.parseInt(lastId.substring(1)) + 1);
			return returnId;
		}
	}
}
