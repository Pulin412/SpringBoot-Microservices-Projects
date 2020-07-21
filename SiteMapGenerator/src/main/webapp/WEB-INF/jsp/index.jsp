<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>Site Map Generator</title>
</head>

<body>
	<form method="post">
		Site Name (https://abc.com / https://www.abc.com) : <input type="text" name="site" id="site" /> <input
			type="submit" value="Generate Site Map" /> (Running time would be several minutes, depending upon the web site)
	</form>

	<c:if test="${!empty siteMap}">
	<h2>${message}</h2>
	<br/>
		<table border="0">
			<tr>
				<th align="left">Links</th>
			</tr>
			<c:forEach items="${siteMap}" var="link">
				<tr>
					<td><c:out value="${link}" /></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>

</html>