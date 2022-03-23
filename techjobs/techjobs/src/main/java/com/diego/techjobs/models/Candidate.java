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
	
	@Column(unique = true, length = 9 )
	private String rg;
	
	@NotEmpty
	@Column(length = 130)
	private String nameCandidate;
	
	@NotEmpty
	@Column(length = 100)
	@Email
	private String email;

	@NotEmpty
	@Column(length = 50)
	private String cargo;
	
	@ManyToOne
	private JobsOportunity jobsOportunity;

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

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
