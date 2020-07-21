/**
 * 
 */
package com.procedure.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.procedure.app.model.Slots;
import com.procedure.app.model.Study;
import com.procedure.app.repository.ProcedureRepository;

/**
 * @author Pulin
 *
 * Service class for Procedure/Study
 */
@Service
public class ProcedureService {

	@Autowired
	private ProcedureRepository procedureRepository;
	
	public List<Study> getAllProcedures() {
		return procedureRepository.getAllProcedures();
	}

	public Study save(Study study) {
		return procedureRepository.save(study);
	}

	public Study findById(String id) {
		return procedureRepository.findById(id);
	}
	
	public Study update(Study study) {
		return procedureRepository.update(study);
	}
	
	/**
	 * @param study
	 * @param model
	 * @return Slots
	 * 
	 *         Method to create the slot on the basis of selected values on UI
	 */
	public Slots createSlot(Study study, Model model) {

		String startSlot = study.getHrsStartTime();
		String endSlot = "";

		/*
         * get the end slot, if user has not selected the end hours, by default
         * 1 hour of slot will be assigned.
         */
		if ("-".equals(study.getHrsEndTime())) {
			endSlot = String.valueOf(Integer.parseInt(study.getHrsStartTime()) + 1);
		} else {
			endSlot = study.getHrsEndTime();
		}

		/* populating Slot object with the created slot */
		Slots slot = new Slots();
		slot.setSlotId(study.getSelectedId());
		List<String> slots = new ArrayList<String>();
		slots.add(startSlot + "-" + endSlot);
		slot.setAvailableSlots(slots);
		return slot;

	}
	
	/**
	 * @param studyList
	 * @param allSlots
	 * @return String list
	 * 
	 *         method to get the available slots for the passed patient/room/doctor.
	 *         Thim method removes the already booked slots on the basis of patient
	 *         or doctor or rooms.
	 */
	public List<String> getSlots(List<Study> studyList, List<String> allSlots) {

		if (studyList != null && studyList.size() > 0) {
			List<String> removeSlots = null;
			for (Study study : studyList) {
				if (study.getSlots() != null) {
					Slots slot = study.getSlots();
					List<String> availableSlots = slot.getAvailableSlots();
					for (String availableSlot : availableSlots) {
						String[] strArr = availableSlot.split("-");
						String startHrs = strArr[0];
						String endHrs = strArr[1];
						removeSlots = new ArrayList<>();

						int startHrsInt = Integer.parseInt(startHrs);
						int endHrsInt = Integer.parseInt(endHrs);
						int count = startHrsInt;

						while (endHrsInt > count) {
							removeSlots.add(String.valueOf(count));
							count++;
						}
					}
					allSlots.removeAll(removeSlots);
				}
			}
		}
		return allSlots;
	}
	
	/**
	 * @param allStudies
	 * @return String
	 * 
	 *         Method to auto generate the Study/procedure Id
	 */
	public String setStudyId(List<Study> allStudies) {

		if (allStudies.size() == 0) {
			return "s1";
		} else {
			Study stud = allStudies.get(allStudies.size() - 1);
			String lastId = stud.getId();
			String returnId = "s" + String.valueOf(Integer.parseInt(lastId.substring(1)) + 1);
			return returnId;
		}
	}
	
	/**
	 * @param study
	 * @param model
	 * @return boolean
	 * 
	 *         method to validate the selected slots.
	 */
	public boolean checkSlots(Study study, Model model) {

		String startSlot = study.getHrsStartTime();
		String endSlot = "";

		if ("-".equals(study.getHrsEndTime())) {
			endSlot = String.valueOf(Integer.parseInt(study.getHrsStartTime()) + 1);
			return true;
		} else {
			if ((Integer.parseInt(study.getHrsStartTime()) > Integer.parseInt(study.getHrsEndTime()))
					|| (study.getHrsStartTime().equals(study.getHrsEndTime()))) {
				return false;
			} else {
				endSlot = study.getHrsEndTime();
				return true;
			}
		}
	}


}
