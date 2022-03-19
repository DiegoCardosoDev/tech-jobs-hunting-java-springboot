package com.diego.techjobs.repository;

import com.diego.techjobs.models.Jobs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends CrudRepository<Jobs, String> {
	Jobs findByCode(long code);
	List<Jobs> findByName(String name);
}
