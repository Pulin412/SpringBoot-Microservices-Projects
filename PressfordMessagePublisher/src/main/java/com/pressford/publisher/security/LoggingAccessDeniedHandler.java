package com.pressford.publisher.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * @author Pulin
 *
 *         Custom Access denied handler to handle the unauthorized access to any
 *         resource.
 *
 *         Returns to a custom page /access-denied.
 *
 */
@Component
public class LoggingAccessDeniedHandler implements AccessDeniedHandler {

	/*
	 *
	 * Custom handler to send to the access denied page in the case of unathorized
	 * access.
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
			throws IOException, ServletException {
		response.sendRedirect(request.getContextPath() + "/access-denied");
	}
}
