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
		<h2>Add a new Patient</h2>
		<h2>${message}</h2>
		<form:form method="POST" action="/addPatient" modelAttribute="patient">
			<table>
				<tr>
					<td><form:label path="id">Patient ID:</form:label></td>
					<td><form:label path="id" value="${patientId}">${patientId}</form:label></td>
					<td><input type="hidden" name="id" value="${patientId}" id="id" /></td>
				</tr>
				<tr>
					<td><form:label path="name">Patient Name:</form:label></td>
					<td><form:input path="name" value="${patient.name}" /></td>
					<td><form:errors path="name" /></td>
				</tr>
				<tr>
					<td><form:label path="sex">Sex:</form:label></td>
					<td><form:select path="sex">
							<form:option value="M" label="M" />
							<form:option value="F" label="F" />
						</form:select></td>
				</tr>
				<tr>
					<td><form:label path="DayOfBirth">Day Of Birth:</form:label></td>
					<td><form:select path="dayOfBirth">
							<form:option value="Monday" label="Monday" />
							<form:option value="Tuesday" label="Tuesday" />
							<form:option value="Wednesday" label="Wednesday" />
							<form:option value="Thursday" label="Thursday" />
							<form:option value="Friday" label="Friday" />
							<form:option value="Saturday" label="Saturday" />
							<form:option value="Sunday" label="Sunday" />
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

	<h2>List of added Patients:</h2>
	<c:if test="${!empty listOfPatients}">
		<table align="center" border="1">
			<tr>
				<th>Patient ID</th>
				<th>Patient Name</th>
				<th>Sex</th>
				<th>Day of Birth</th>
			</tr>

			<c:forEach items="${listOfPatients}" var="patient">
				<tr>
					<td><c:out value="${patient.id}" /></td>
					<td><c:out value="${patient.name}" /></td>
					<td><c:out value="${patient.sex}" /></td>
					<td><c:out value="${patient.dayOfBirth}" /></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>