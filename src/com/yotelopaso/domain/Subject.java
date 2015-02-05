package com.yotelopaso.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Subject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@NotNull
	private String name;
	
	@NotNull
	@ManyToOne
	private Career career;
	
	@NotNull
	private Integer year;
	
	@ManyToMany(mappedBy="subscriptedSubjects")
	private Set<User> subscriptors = new HashSet<User>();
	
	public Subject() {}

	/**
	 * @param id
	 * @param name
	 * @param career
	 * @param year
	 */
	public Subject(Integer id, String name, Career career, Integer year) {
		this.id = id;
		this.name = name;
		this.career = career;
		this.year = year;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Career getCareer() {
		return career;
	}

	public void setCareer(Career career) {
		this.career = career;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public Set<User> getSubscriptors() {
		return subscriptors;
	}

	public void setSubscriptors(Set<User> subscriptors) {
		this.subscriptors = subscriptors;
	}

}
