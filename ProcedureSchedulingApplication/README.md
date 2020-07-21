											Assumptions taken before development

1. Only hours are provided to the user for selection of time slots.

2. 1 hour is the default slot i.e. if user doesn't selects the end time, 1 hour would be saved.

3. Timings are 7 to 22 hrs i.e. 7 am to 10 pm.

4. Rooms and Doctors are pre-defined and are persisted via script.

5. Slot booking is on the basis of Patients, rooms and doctor i.e if for one booking Patient (p1), room (r1). doctor (d1) is used
then for the second time if any one of this combination (p1/r1/d1) is present in the next slot booking, that booked slot time will not 
be shown to the user.
Eg. 
	(p1,r1,d1) - 7-8 am
	
	(p1,r2,d2) - 8 onwards (since p1 is already booked from 7-8 so that slot will not be visible)
	OR
	(p2,r1,d2) - 8 onwards (since r1 is already booked from 7-8 so that slot will not be visible)
	OR
	(p2,r2,d1) - 8 onwards (since d1 is already booked from 7-8 so that slot will not be visible)
	
----------------------------------------------------------------------------------------------------------------------------------------

- Use "mvn spring-boot:run" to run the application via CMD.