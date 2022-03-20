package com.diego.techjobs.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class Candidate {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(unique = true)
	private String rg;
	
	@NotEmpty
	private String nameCandidate;
	
	@NotEmpty
	@Email
	private String email;
	
	@ManyToOne
	private JobsOportunity jobsOportunity;

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNameCandidate() {
		return nameCandidate;
	}

	public void setNameCandidate(String nameCandidate) {
		this.nameCandidate = nameCandidate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public JobsOportunity getVaga() {
		return jobsOportunity;
	}

	public void setVaga(JobsOportunity jobsOportunity) {
		this.jobsOportunity = jobsOportunity;
	}
	
	

}
