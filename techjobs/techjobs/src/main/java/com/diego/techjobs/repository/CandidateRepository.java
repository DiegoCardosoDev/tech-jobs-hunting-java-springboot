package com.diego.techjobs.repository;

import com.diego.techjobs.models.Candidate;
import com.diego.techjobs.models.JobsOportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CandidateRepository extends JpaRepository<Candidate, String> {
	
	Iterable<Candidate> findByjobsOportunity(JobsOportunity jobsOportunity);
	
	Candidate findByRg(String rg);
	
	Candidate findById(long id);
	
	List<Candidate> findBynameCandidate(String nameCandidate);
}
