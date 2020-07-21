/*
 * COPYRIGHT. HSBC HOLDINGS PLC 2019. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of HSBC Holdings plc.
 */
package com.procedure.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.procedure.app.model.Doctor;
import com.procedure.app.model.Patient;
import com.procedure.app.model.Room;
import com.procedure.app.repository.PatientRepository;
import com.procedure.app.service.PatientService;

import junit.framework.Assert;

/**
 * <p>
 * <b> TODO : Insert description of the class's responsibility/role. </b>
 * </p>
 */
@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
public class PatientServiceTest {

    @TestConfiguration
    static class PatientServiceTestContextConfiguration {

        @Bean
        public PatientService patientService() {
            return new PatientService();
        }
    }

    @Autowired
    private PatientService patientService;

    @MockBean
    private PatientRepository patientRepository;


    @Before
    public void setUp() {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(new Patient("p1", "pat1", "M", "Sunday"));
        Mockito.when(this.patientRepository.getAllPatients()).thenReturn(patientList);

        Patient p = new Patient("p1", "pat1", "M", "Sunday");
        Mockito.when(this.patientRepository.findById("p1")).thenReturn(p);

        Doctor d = new Doctor("d1", "doc1");
        Mockito.when(this.patientRepository.findDoctorById("d1")).thenReturn(d);

        Room r = new Room("r1", "room1");
        Mockito.when(this.patientRepository.findRoomById("r1")).thenReturn(r);

        Mockito.when(this.patientRepository.save(Mockito.any(Patient.class))).thenReturn(new Patient());
    }

    @Test
    public void whenAllPatientsGetCalled_thenAllPatientsShouldBeReturned() {

        List<Patient> patientList = this.patientService.getAllPatients();

        Assert.assertEquals(patientList.size(), 1);
    }

    @Test
    public void whenValidIdGiven_thenPatientShouldBeReturned() {

        Patient p = this.patientService.findById("p1");

        Assert.assertEquals(p.getId(), "p1");
    }

    @Test
    public void whenValidDoctorIdGiven_thenDoctorShouldBeReturned() {

        Doctor d = this.patientService.findDoctorById("d1");

        Assert.assertEquals(d.getId(), "d1");
    }

    @Test
    public void whenValidRoomIdGiven_thenRoomShouldBeReturned() {

        Room r = this.patientService.findRoomById("r1");

        Assert.assertEquals(r.getId(), "r1");
    }

    @Test
    public void whenSavePatient_thenTrueShouldBeReturned() {

        Patient p = new Patient();
        Assert.assertNotNull(this.patientService.save(p));
    }
}
