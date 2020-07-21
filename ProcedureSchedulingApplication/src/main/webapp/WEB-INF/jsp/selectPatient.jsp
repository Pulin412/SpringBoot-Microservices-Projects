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
		<h2>Select the patient</h2>
		<form:form method="POST" action="/selectPatient"
			modelAttribute="selectModel">
			<table>
				<tr>
					<td><form:label path="patientId">Patient ID:</form:label></td>
					<td><form:select path="patientId">
							<form:options items="${patientList}" />
						</form:select></td>
				</tr>
				<tr>
					<td><form:label path="doctorId">Doctor ID:</form:label></td>
					<td><form:select path="doctorId">
							<form:option value="d1" label="Doc1" />
							<form:option value="d2" label="Doc2" />
							<form:option value="d3" label="Doc3" />
							<form:option value="d4" label="Doc4" />
						</form:select></td>
				</tr>
				<tr>
					<td><form:label path="roomId">Room ID:</form:label></td>
					<td><form:select path="roomId">
							<form:option value="r1" label="Room1" />
							<form:option value="r2" label="Room2" />
							<form:option value="r3" label="Room3" />
							<form:option value="r4" label="Room4" />
						</form:select></td>
				</tr>
				<tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" value="Schedule" /></td>
				</tr>
			</table>
		</form:form>
	</center>
</body>
</html>