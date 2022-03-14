package com.diego.techjobs.repository;

import com.diego.techjobs.models.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, Long> {
    Jobs findByCode(Long code);
    List<Jobs> findByName(String name);
}
