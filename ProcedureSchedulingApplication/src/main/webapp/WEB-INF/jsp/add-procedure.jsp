<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Procedure Scheduling Application</title>
</head>
<body>

	<center><a href="/">Home</a></center><br>
	<center>
		<h2>Schedule a new Procedure</h2>
		<h2>${message}</h2>
		<form:form method="POST" action="/addProcedure" modelAttribute="study">
			<table>
				<tr>
					<td><form:label path="id">Study ID:</form:label></td>
					<td><form:label path="id" value="${studyId}">${studyId}</form:label></td>
					<td><input type="hidden" name="id" value="${studyId}" id="id" /></td>
				</tr>
				<tr>
					<td><form:label path="selectedId">Patient Id:</form:label></td>
					<td><form:label path="selectedId" value="${selectedPatientId}">${selectedPatientId}</form:label></td>
					<td><input type="hidden" name="selectedId"
						value="${selectedPatientId}" id="selectedId" /></td>
				</tr>
				<tr>
					<td><form:label path="Description">Description:</form:label></td>
					<td><form:input path="Description"
							value="${study.description}" /></td>
					<td><form:errors path="description" /></td>
				</tr>
				<tr>
					<td><form:label path="Status">Status:</form:label></td>
					<td><form:select path="Status">
							<form:option value="Planned" label="Planned" />
							<form:option value="In Progress" label="In Progress" />
							<form:option value="Finished" label="Finished" />
						</form:select></td>
				</tr>
				<tr>
					<td><label>Planned Start Time (Hours):</label></td>
					<td><form:select path="hrsStartTime">
							<form:options items="${allSlots}" />
						</form:select></td>
				</tr>
				<tr>
					<td><label>Estimated End Time (Hours):</label></td>
					<td><form:select path="hrsEndTime">
							<form:option value="-" label="-select-" />
							<form:options items="${allSlots}" />
						</form:select></td>
				</tr>
				<tr>
					<td><form:label path="selectedDoctorId">Doctor Id:</form:label></td>
					<td><form:label path="selectedDoctorId"
							value="${selectedDoctorId}">${selectedDoctorId}</form:label></td>
					<td><input type="hidden" name="selectedDoctorId"
						value="${selectedDoctorId}" id="selectedDoctorId" /></td>
				</tr>
				<tr>
					<td><form:label path="selectedRoomId">Room Id:</form:label></td>
					<td><form:label path="selectedRoomId"
							value="${selectedRoomId}">${selectedRoomId}</form:label></td>
					<td><input type="hidden" name="selectedRoomId"
						value="${selectedRoomId}" id="selectedRoomId" /></td>
				</tr>
				<tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" value="SAVE" /></td>
				</tr>
			</table>
		</form:form>
	</center>

	<c:if test="${!empty listOfProcedures}">
		<h2>List of already scheduled Procedures:</h2>
		<table align="center" border="1">
			<tr>
				<th>Study ID</th>
				<th>Patient ID</th>
				<th>Doctor ID</th>
				<th>Room ID</th>
				<th>Status</th>
				<th>Schedule</th>
			</tr>

			<c:forEach items="${listOfProcedures}" var="study">
				<tr>
					<td><c:out value="${study.id}" /></td>
					<td><c:out value="${study.selectedId}" /></td>
					<td><c:out value="${study.selectedDoctorId}" /></td>
					<td><c:out value="${study.selectedRoomId}" /></td>
					<td><c:out value="${study.slots.availableSlots}" /></td>
					<td><c:out value="${study.status}" /></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>