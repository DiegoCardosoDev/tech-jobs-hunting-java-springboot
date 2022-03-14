package com.diego.techjobs.repository;

import com.diego.techjobs.models.Candidate;
import com.diego.techjobs.models.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, String> {

    Iterable<Candidate> findByJobs(Jobs jobs);
    Candidate findByRg(String rg);
    Candidate findById(Long id);
//    List<Candidate> findByname(String name);


}
