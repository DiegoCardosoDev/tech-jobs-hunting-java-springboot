package com.diego.techjobs.repository;

import com.diego.techjobs.models.JobsOportunity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends CrudRepository<JobsOportunity, String> {
	JobsOportunity findByCode(long code);
	List<JobsOportunity> findByName(String name);
}
