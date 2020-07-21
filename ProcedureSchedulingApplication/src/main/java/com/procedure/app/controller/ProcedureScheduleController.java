package com.procedure.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.procedure.app.model.Doctor;
import com.procedure.app.model.Patient;
import com.procedure.app.model.Room;
import com.procedure.app.model.SelectModel;
import com.procedure.app.model.Slots;
import com.procedure.app.model.Study;
import com.procedure.app.service.PatientService;
import com.procedure.app.service.ProcedureService;

/**
 * 
 * @author Pulin Controller class to add/update Schedules(Study) , add patients.
 * 
 */

@Controller
public class ProcedureScheduleController {

	@Autowired
	private ProcedureService procedureService;

	/**
	 * 
	 * @param procedureService the procedureService to set
	 */
	public void setProcedureService(final ProcedureService procedureService) {
		this.procedureService = procedureService;
	}

	/**
	 * 
	 * @param patientService the patientService to set
	 */
	public void setPatientService(final PatientService patientService) {
		this.patientService = patientService;
	}

	@Autowired
	private PatientService patientService;

	/**
	 * 
	 * @param model
	 * @return view GET end point for the index page.
	 */
	@GetMapping("/")
	public String goToHomePage(final Model model) {
		model.addAttribute("study", new Study());
		model.addAttribute("listOfProcedures", this.procedureService.getAllProcedures());
		return "index";

	}

	/**
	 * @param model
	 * @return view GET end point to get all the saved procedures/studies
	 */
	@GetMapping("/getAllProcedures")
	public String getAllProcedures(final Model model) {
		model.addAttribute("study", new Study());
		model.addAttribute("listOfProcedures", this.procedureService.getAllProcedures());
		return "procedureDetails";
	}

	/**
	 * @param message
	 * @return view GET end point to select the patient, room and doctor
	 */
	@GetMapping("/selectPatient")
	public String selectPatient(final Model model) {
		model.addAttribute("selectModel", new SelectModel());
		return "selectPatient";
	}

	/**
	 * @param message
	 * @return view POST end point to submit the details selected by the user in
	 *         select patient/doctor/room view
	 */
	@PostMapping("/selectPatient")
	public String schedule(final Model model, @ModelAttribute final SelectModel selectModel,
			final RedirectAttributes redirectAttributes) {
		/*
		 * Get the patient, room and doctor objects on the basis of selected values on
		 * UI
		 */
		Patient patient = this.patientService.findById(selectModel.getPatientId());
		Doctor doctor = this.patientService.findDoctorById(selectModel.getDoctorId());
		Room room = this.patientService.findRoomById(selectModel.getRoomId());

		List<Study> studyList;
		List<String> allSlots = null;
		List<Study> doctorStudyList;
		List<Study> roomStudyList;

		/*
		 * Get the booked slots for the selected patient, doctor and room. Filter the
		 * slots which are not conflicting with the already booked slots.
		 */
		if (patient != null) {
			studyList = patient.getStudy();
			allSlots = this.procedureService.getSlots(studyList, gethours());
		}

		if (doctor != null) {
			doctorStudyList = doctor.getStudy();
			this.procedureService.getSlots(doctorStudyList, allSlots);
		}

		if (room != null) {
			roomStudyList = room.getStudy();
			this.procedureService.getSlots(roomStudyList, allSlots);
		}

		model.addAttribute("study", new Study());
		redirectAttributes.addFlashAttribute("patientId", selectModel.getPatientId());
		redirectAttributes.addFlashAttribute("doctorId", selectModel.getDoctorId());
		redirectAttributes.addFlashAttribute("roomId", selectModel.getRoomId());
		redirectAttributes.addFlashAttribute("allSlots", allSlots);
		return "redirect:addProcedure";
	}

	/**
	 * @param message
	 * @return view
	 * 
	 *         GET method to view the page for adding a procedure/study with the
	 *         appropriate slots value
	 */
	@GetMapping("/addProcedure")
	public String addProcedure(final HttpServletRequest request, final Model model) {

		/*
		 * Getting the details from the SelectPatient page along with the filtered
		 * slots.
		 */
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		String patientId = (String) flashMap.get("patientId");
		String doctorId = (String) flashMap.get("doctorId");
		String roomId = (String) flashMap.get("roomId");
		List<String> allSlots = (List<String>) flashMap.get("allSlots");
		List<Study> allStudies = this.procedureService.getAllProcedures();

		String studyId = this.procedureService.setStudyId(allStudies);

		Study study = new Study();
		study.setPatient(this.patientService.findById(patientId));
		study.setDoctor(this.patientService.findDoctorById(doctorId));
		study.setRoom(this.patientService.findRoomById(roomId));

		model.addAttribute("selectedPatientId", patientId);
		model.addAttribute("selectedRoomId", roomId);
		model.addAttribute("selectedDoctorId", doctorId);
		model.addAttribute("allSlots", allSlots);
		model.addAttribute("studyId", studyId);
		model.addAttribute("study", study);
		model.addAttribute("listOfProcedures", this.procedureService.getAllProcedures());
		return "add-procedure";
	}

	/**
	 * @param message
	 * @param result
	 * @param model
	 * 
	 * @return Index view or Add message view according to the errors
	 */

	@PostMapping("/addProcedure")
	public String addProcedure(@ModelAttribute @Valid final Study study, final BindingResult result,
			final Model model) {

		/*
		 * Creating the proper slots on the basis of values selected from the hours drop
		 * down on UI
		 */

		Slots bookedSlots = this.procedureService.createSlot(study, model);

		/* Populating the Study object */
		study.setPatient(this.patientService.findById(study.getSelectedId()));
		study.setDoctor(this.patientService.findDoctorById(study.getSelectedDoctorId()));
		study.setRoom(this.patientService.findRoomById(study.getSelectedRoomId()));
		study.setSlots(bookedSlots);

		/* Adding model attributes */
		model.addAttribute("selectedPatientId", study.getSelectedId());
		model.addAttribute("selectedRoomId", study.getSelectedRoomId());
		model.addAttribute("selectedDoctorId", study.getSelectedDoctorId());
		model.addAttribute("allSlots", gethours());
		model.addAttribute("studyId", study.getId());
		model.addAttribute("study", study);
		model.addAttribute("listOfProcedures", this.procedureService.getAllProcedures());

		/* checking for any errors */
		if (result.hasErrors()) {
			return "add-procedure";
		}

		try {
			/*
			 * checking if the selected slots are valid or not. If start hour is greater or
			 * equal to end hour, error will be shown to user.
			 */
			if (!this.procedureService.checkSlots(study, model)) {
				model.addAttribute("message", "End hours should be higher than Start hours!");
				return "add-procedure";
			}

			/* persisting Study object */
			this.procedureService.save(study);
			model.addAttribute("message", "Study scheduled successfully");
		} catch (Exception ex) {
			model.addAttribute("message", "Error occured while scheduling Study");
		}

		model.addAttribute("listOfProcedures", this.procedureService.getAllProcedures());
		return "index";
	}

	/**
	 * @param id
	 * @param model
	 * @return update message view Request Mapping to show the update page on click
	 *         of update icon.
	 */

	@GetMapping("/edit/{id}")
	public String updateProcedure(@PathVariable("id") final String id, final Model model) {

		/*
		 * Get the patient, room and doctor objects on the basis of selected values on
		 * UI
		 */
		Study study = this.procedureService.findById(id);
		Patient patient = this.patientService.findById(study.getSelectedId());
		Doctor doctor = this.patientService.findDoctorById(study.getSelectedDoctorId());
		Room room = this.patientService.findRoomById(study.getSelectedRoomId());

		List<Study> studyList;
		List<String> allSlots = null;
		List<Study> doctorStudyList;
		List<Study> roomStudyList;

		/*
		 * Get the booked slots for the selected patient, doctor and room. Filter the
		 * slots which are not conflicting with the already booked slots.
		 */
		if (patient != null) {
			studyList = patient.getStudy();
			allSlots = this.procedureService.getSlots(studyList, gethours());
		}

		if (doctor != null) {
			doctorStudyList = doctor.getStudy();
			this.procedureService.getSlots(doctorStudyList, allSlots);
		}

		if (room != null) {
			roomStudyList = room.getStudy();
			this.procedureService.getSlots(roomStudyList, allSlots);
		}

		model.addAttribute("study", study);
		model.addAttribute("selectedPatientId", study.getPatient().getId());
		model.addAttribute("allSlots", allSlots);
		return "update-procedure";

	}

	/**
	 * @param id
	 * @param message
	 * @param result
	 * @param model
	 * @return Index view or the update message view according to the errors
	 */
	@PostMapping("/updateProcedure/{id}")
	public String updateProcedure(@PathVariable("id") final String id, @ModelAttribute @Valid final Study study,
			final BindingResult result, final Model model) {

		model.addAttribute("allSlots", gethours());

		/* checking for any errors */
		if (result.hasErrors()) {
			study.setId(id);
			model.addAttribute("selectedPatientId", study.getPatient().getId());
			model.addAttribute("study", study);
			return "update-procedure";
		}

		try {
			/*
			 * Creating the proper slots on the basis of values selected from the hours drop
			 * down on UI
			 */
			Slots bookedSlots = this.procedureService.createSlot(study, model);

			/* Populating the Study object */
			study.setPatient(this.patientService.findById(study.getSelectedId()));
			study.setDoctor(this.patientService.findDoctorById(study.getSelectedDoctorId()));
			study.setRoom(this.patientService.findRoomById(study.getSelectedRoomId()));
			study.setSlots(bookedSlots);

			/*
			 * checking if the selected slots are valid or not. If start hour is greater or
			 * equal to end hour, error will be shown to user.
			 */
			if (!this.procedureService.checkSlots(study, model)) {
				study.setId(id);
				model.addAttribute("selectedPatientId", study.getPatient().getId());
				model.addAttribute("study", study);
				model.addAttribute("message", "End hours should be higher than Start hours!");
				return "update-procedure";
			}

			/* Updating the study object */
			this.procedureService.update(study);
			model.addAttribute("selectedPatientId", study.getSelectedId());
			model.addAttribute("message", "Study scheduled successfully");
		} catch (Exception ex) {
			model.addAttribute("message", "Error occured while scheduling Study");
		}
		model.addAttribute("listOfProcedures", this.procedureService.getAllProcedures());
		return "update-procedure";
	}

	/**
	 * @param message
	 * @return view GET method to get the add patient page
	 * 
	 */
	@GetMapping("/addPatient")
	public String addPatient(final Model model) {

		List<Patient> patientList = this.patientService.getAllPatients();
		model.addAttribute("patient", new Patient());
		model.addAttribute("listOfPatients", patientList);
		model.addAttribute("patientId", this.patientService.setPatientId(patientList));
		return "add-patient";

	}

	/**
	 * @param message
	 * @param result
	 * @param model
	 * @return Index view or Add message view according to the errors
	 * 
	 *         POST method to add patients
	 */
	@PostMapping("/addPatient")
	public String addPatient(@Valid @ModelAttribute final Patient patient, final BindingResult result,
			final Model model) {

		/* checking for errors */
		if (result.hasErrors()) {
			model.addAttribute("patientId", this.patientService.setPatientId(this.patientService.getAllPatients()));
			model.addAttribute("listOfPatients", this.patientService.getAllPatients());
			return "add-patient";
		}

		try {
			/* persisting patient object */
			this.patientService.save(patient);
			model.addAttribute("listOfPatients", this.patientService.getAllPatients());
			model.addAttribute("patientId", this.patientService.setPatientId(this.patientService.getAllPatients()));
			model.addAttribute("message", "Patient added successfully");
		} catch (Exception ex) {
			model.addAttribute("message", "Error occured while adding Patient");
		}
		return "add-patient";
	}

	/**
	 * @return List of patient IDs.
	 * 
	 *         This method converts the list of patient objects into the list of
	 *         patient ids.
	 */
	@ModelAttribute("patientList")
	public List<String> getPatientsList() {
		List<Patient> patientList = this.patientService.getAllPatients();
		return patientList.stream().map(p -> p.getId()).collect(Collectors.toList());
	}

	/**
	 * @return List of Slots as string.
	 */
	@ModelAttribute("hours")
	public List<String> gethours() {
		List<String> slotList = new ArrayList<String>();
		slotList.add("7");
		slotList.add("8");
		slotList.add("9");
		slotList.add("10");
		slotList.add("11");
		slotList.add("12");
		slotList.add("13");
		slotList.add("14");
		slotList.add("15");
		slotList.add("16");
		slotList.add("17");
		slotList.add("18");
		slotList.add("19");
		slotList.add("20");
		slotList.add("21");
		slotList.add("22");
		return slotList;
	}
}