package de.cwerl.complexityzoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.cwerl.complexityzoo.model.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {
    
}
