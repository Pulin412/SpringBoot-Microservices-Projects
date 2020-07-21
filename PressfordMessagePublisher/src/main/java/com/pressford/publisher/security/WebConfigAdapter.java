package com.pressford.publisher.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Component
public class WebConfigAdapter extends WebMvcConfigurerAdapter {

	/**
	 * @param registry
	 *
	 *            Map the view controller to the given URL path ("/") in order to
	 *            redirect to another URL ("/home").
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/home");
	}

}
