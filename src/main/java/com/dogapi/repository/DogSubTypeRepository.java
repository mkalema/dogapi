package com.dogapi.repository;

import com.dogapi.model.DogSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogSubTypeRepository extends JpaRepository<DogSubType, Long> {
}
