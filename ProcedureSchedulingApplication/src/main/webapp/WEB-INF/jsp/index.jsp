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

	<h2>
		<a href="selectPatient">Schedule a new Procedure</a> 
		<br>
		<a href="addPatient">Add a new Patient</a>
	</h2>
	<br />
	<center>

		<c:if test="${!empty listOfProcedures}">
			<h4>Scheduled Procedures:</h4>
			<table align="center" border="1">
				<tr>
					<th>Study ID</th>
					<th>Patient ID</th>
					<th>Doctor ID</th>
					<th>Room ID</th>
					<th>Schedule</th>
					<th>Status</th>
					<th>Options</th>
				</tr>

				<c:forEach items="${listOfProcedures}" var="study">
					<tr>
						<td><c:out value="${study.id}" /></td>
						<td><c:out value="${study.selectedId}" /></td>
						<td><c:out value="${study.selectedDoctorId}" /></td>
						<td><c:out value="${study.selectedRoomId}" /></td>
						<td><c:out value="${study.slots.availableSlots}" /></td>
						<td><c:out value="${study.status}" /></td>
						<td align="center"><a href="edit/${study.id}">Edit</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>

	</center>
</body>
</html>