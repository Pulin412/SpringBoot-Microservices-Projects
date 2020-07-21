package com.pressford.publisher.utility;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import com.googlecode.charts4j.Color;
import com.pressford.publisher.entities.User;

/**
 * @author Pulin
 *
 *         Utility class.
 *
 */
public class Utility {

	/**
	 * @return Logged in User name
	 *
	 *         Method to get the authenticated user name.
	 */
	public static String getUserFromContext() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return auth.getName();
		}
		return null;
	}

	/**
	 * @return Logged in User role
	 *
	 *         Method to get the authenticated user role.
	 */
	public static Collection<? extends GrantedAuthority> getRoleFromContext() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return auth.getAuthorities();
		}
		return null;
	}

	/**
	 * @param user
	 * @return User
	 *
	 *         Adjust the likes for the users, decrease the remaining likes and
	 *         increase the available likes
	 */
	public static User adjustLikes(User user) {
		int likesUsed = user.getLikesUsed();
		int remainingLikes = user.getRemainingLikes();
		user.setLikesUsed(++likesUsed);
		user.setRemainingLikes(--remainingLikes);
		return user;
	}

	/**
	 * @param model
	 * @return Model
	 *
	 *         Set the attributes on the basis of logged in user and returns the
	 *         model.
	 */
	public static Model setModelBasedOnRole(Model model) {
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) Utility.getRoleFromContext();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority() != null
					&& "ROLE_PUBLISHER".equalsIgnoreCase(grantedAuthority.getAuthority())) {
				model.addAttribute("role", "admin");
			} else {
				model.addAttribute("role", "user");
			}
		}
		return model;
	}

	/**
	 * @param i
	 * @return new Color
	 *
	 *         Getting new color everytime for the pie chart
	 */
	public static Color getColor(int i) {

		switch (i) {
		case 0:
			return Color.newColor("CACACA");
		case 1:
			return Color.newColor("DF7417");
		case 2:
			return Color.newColor("951800");
		case 3:
			return Color.newColor("01A1DB");
		case 4:
			return Color.newColor("F0F8FF");
		case 5:
			return Color.newColor("FAEBD7");
		case 6:
			return Color.newColor("00FFFF");
		case 7:
			return Color.newColor("7FFFD4");
		case 8:
			return Color.newColor("F0FFFF");
		case 9:
			return Color.newColor("F5F5DC");
		case 10:
			return Color.newColor("FFE4C4");
		case 11:
			return Color.newColor("000000");
		case 12:
			return Color.newColor("0000FF");
		case 13:
			return Color.newColor("A52A2A");
		case 14:
			return Color.newColor("DEB887");
		case 15:
			return Color.newColor("5F9EA0");
		case 16:
			return Color.newColor("7FFF00");
		case 17:
			return Color.newColor("D2691E");
		case 18:
			return Color.newColor("FF7F50");
		case 19:
			return Color.newColor("6495ED");
		case 20:
			return Color.newColor("FFF8DC");
		default:
			return Color.newColor("008000");
		}
	}

}
