package com.techouts.aemindustry.core.students;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class)
public class Student {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	@Optional
	private String studentname;

	@Inject
	@Optional
	@Named("studentrollno")
	private String studentrollno;

	@Inject
	@Optional
	private List<Resource> subjects;

	@Optional
	private List<Subject> subjectList = new ArrayList<>();
	
	
	@PostConstruct
	protected void init() {
		log.debug("In Init Method of Student model.");

		if (!subjects.isEmpty()) {
			for (Resource resource : subjects) {

				Subject subject = resource.adaptTo(Subject.class);
				subjectList.add(subject);

			}
		}

	}

	
	

	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getStudentrollno() {
		return studentrollno;
	}

	public void setStudentrollno(String studentrollno) {
		this.studentrollno = studentrollno;
	}

	public List<Resource> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Resource> subjects) {
		this.subjects = subjects;
	}

	public List<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	

}
