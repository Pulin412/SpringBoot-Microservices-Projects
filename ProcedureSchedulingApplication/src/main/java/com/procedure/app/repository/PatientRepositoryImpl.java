/**
 * 
 */
package com.procedure.app.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.procedure.app.model.Doctor;
import com.procedure.app.model.Patient;
import com.procedure.app.model.Room;
import com.procedure.app.model.Study;

/**
 * @author Pulin
 *
 * Repository Interface implementation for Patient/Room/Doctor related methods
 */
@Repository
@Transactional
public class PatientRepositoryImpl implements PatientRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

	@Override
	public Patient save(Patient patient) {
		getSession().save(patient);
		return patient;
	}

	@Override
	public List<Patient> getAllPatients() {
		return getSession().createQuery("from Patient").list();
	}

	@Override
	public Patient findById(String selectedId) {
		return getSession().get(Patient.class, selectedId);
	}

	@Override
	public Room findRoomById(String selectedId) {
		return getSession().get(Room.class, selectedId);
	}

	@Override
	public Doctor findDoctorById(String selectedId) {
		return getSession().get(Doctor.class, selectedId);
	}
}
