package com.yotelopaso.persistence;

import java.util.HashSet;
import java.util.Set;

import com.vaadin.server.VaadinSession;
import com.yotelopaso.domain.Subject;
import com.yotelopaso.domain.User;

public class UserManager extends DataManager<User> {
	
	public UserManager() {
		super(User.class);
	}
	
	public boolean isRegistered(Double id) {
		User user = getById(id);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void logInUser(Double userId) {
		VaadinSession.getCurrent().setAttribute("userId", userId);
	}
	
	public User getCurrentUser() {
		return (User) VaadinSession.getCurrent().getAttribute("currentUser");
	}
	
	public void setCurrentUser(User currentUser) {
		VaadinSession.getCurrent().setAttribute("currentUser", currentUser);
	}
	
	public Set<Subject> getCurrentUserSubjects() {
		return getCurrentUser().getSubscriptedSubjects();
	}
	
	public void setCurrentUserSubjects(Set<Subject> subjects) {
		User cu = getCurrentUser();
		cu.setSubscriptedSubjects(subjects);
		save(cu);
		setCurrentUser(cu);
	}
	
	public void setNewUserDefaultSubjects(User user, String careerName, Integer year) {
		SubjectManager sm = new SubjectManager();
		Set<Subject> subjects = new HashSet<Subject>(sm.filterByCareerAndYear(careerName, year));
		user.addSubjects(subjects);
	}

}
