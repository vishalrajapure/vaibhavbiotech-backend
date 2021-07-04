package com.vaibhavbiotech.repository;

import com.vaibhavbiotech.models.ClientSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientSequenceRepository extends JpaRepository<ClientSequence, Long> {
}
