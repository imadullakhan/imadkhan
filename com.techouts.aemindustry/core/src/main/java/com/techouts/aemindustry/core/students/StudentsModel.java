package com.techouts.aemindustry.core.students;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class)
public class StudentsModel {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	@Named("sling:resourceType")
	@Default(values = "No resourceType")
	protected String resourceType;

	@Inject
	@Optional
	private List<Resource> students;

	private List<Student> studentList = new ArrayList<>();

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	@PostConstruct
	protected void init() {

		logger.debug("In init of studentModel");
		if (!students.isEmpty()) {

			for (Resource resource : students) {

				Student student = resource.adaptTo(Student.class);

				studentList.add(student);

			}
		}

	}

}
