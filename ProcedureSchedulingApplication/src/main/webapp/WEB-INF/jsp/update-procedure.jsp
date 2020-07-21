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
		<h2>Update Procedure</h2>
		<h2>${message}</h2>
		<form:form method="POST" action="/updateProcedure/${study.id}"
			modelAttribute="study">
			<table>
				<tr>
					<td><form:label path="id">Study ID:</form:label></td>
					<td><form:label path="id" value="${study.id}">${study.id}</form:label></td>
					<td><input type="hidden" name="id" value="${study.id}" id="id" /></td>
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
					<td><form:errors path="Description" /></td>
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
					<td><label>PlannedStartTime:</label></td>
					<td><form:select path="hrsStartTime">
							<form:options items="${allSlots}" />
						</form:select></td>
				</tr>
				<tr>
					<td><label>EstimatedEndTime:</label></td>
					<td><form:select path="hrsEndTime">
							<form:option value="-" label="-select-" />
							<form:options items="${allSlots}" />
						</form:select></td>
				</tr>
				<tr>
					<td><form:label path="selectedDoctorId">Doctor ID:</form:label></td>
					<td><form:select path="selectedDoctorId">
							<form:option value="d1" label="Doc1" />
							<form:option value="d2" label="Doc2" />
							<form:option value="d3" label="Doc3" />
							<form:option value="d4" label="Doc4" />
						</form:select></td>
				</tr>
				<tr>
					<td><form:label path="selectedRoomId">Room ID:</form:label></td>
					<td><form:select path="selectedRoomId">
							<form:option value="r1" label="Room1" />
							<form:option value="r2" label="Room2" />
							<form:option value="r3" label="Room3" />
							<form:option value="r4" label="Room4" />
						</form:select></td>
				</tr>
				<tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" value="SAVE" /></td>
				</tr>
			</table>
		</form:form>
	</center>
</body>
</html>