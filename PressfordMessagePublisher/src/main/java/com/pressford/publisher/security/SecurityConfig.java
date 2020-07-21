package com.pressford.publisher.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Pulin
 *
 *         Custom security configuration class.
 *
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${pages.permil.all}")
	private String[] pagesPermitAll;

	@Value("${pages.permit.publisher}")
	private String[] pagesPermitPublisher;

	@Value("#{${pressford.user.map}}")
	private HashMap<String, String> users;

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

	/**
	 * @return BCrypt Password Encoder
	 *
	 *         Encoding the password using BCryptPasswordEncoder.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * @param auth
	 * @throws Exception
	 *
	 *             Uses inmemory authentication to for roles based access to the
	 *             users. Users are created as per the given map in
	 *             application.properties file
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth = buildUser(users, auth);

	}

	/**
	 * @param users
	 * @param auth
	 * @return AuthenticationManagerBuilder
	 * @throws Exception
	 *
	 *             Returns the in Memory Authentication Builder with newly created
	 *             users/admins given in the application.properties
	 */
	private AuthenticationManagerBuilder buildUser(HashMap<String, String> users, AuthenticationManagerBuilder auth)
			throws Exception {

		UserBuilder builder = null;
		for (Map.Entry<String, String> entry : users.entrySet()) {
			String val = entry.getValue();
			if (val != null && !"".equals(val)) {
				String[] strArr = val.split(",");
				builder = org.springframework.security.core.userdetails.User.withUsername(strArr[0]);
				builder.password(passwordEncoder().encode(strArr[1]));
				builder.roles(strArr[2]);
				builder.build();
				auth.inMemoryAuthentication().withUser(builder);
			}
		}
		return auth;
	}

	/**
	 * @param auth
	 * @throws Exception
	 *
	 *             Custom configure method to give access to all for the home, js,
	 *             css, ims, webjars files. signup, update, delete to a user with
	 *             Publisher permission Used custom login page Custom Accessdenied
	 *             handler is used
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers(pagesPermitAll)
			.permitAll()
			.antMatchers(pagesPermitPublisher)
			.hasRole("PUBLISHER")
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/signin")
			.permitAll()
			.and()
			.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/signin?logout")
			.permitAll()
			.and()
			.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler);
	}
}
