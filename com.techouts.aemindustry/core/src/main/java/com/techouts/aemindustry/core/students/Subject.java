package com.techouts.aemindustry.core.students;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Subject {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
    @Optional
    private String subjectgrade;

    @Inject
    @Optional
    private String subjectmarks;

    @Inject
    @Optional
	private String subjectname;

    @Inject
    @Optional
    private String subjecteacher;

	
	
	public String getSubjectgrade() {
		return subjectgrade;
	}



	public void setSubjectgrade(String subjectgrade) {
		this.subjectgrade = subjectgrade;
	}



	public String getSubjectmarks() {
		return subjectmarks;
	}



	public void setSubjectmarks(String subjectmarks) {
		this.subjectmarks = subjectmarks;
	}



	public String getSubjectname() {
		return subjectname;
	}



	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}



	public String getSubjecteacher() {
		return subjecteacher;
	}



	public void setSubjecteacher(String subjecteacher) {
		this.subjecteacher = subjecteacher;
	}



	@PostConstruct
	protected void init(){
		
		logger.debug("In init of Subject Model ");
		
	}

}
