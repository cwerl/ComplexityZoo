package de.cwerl.complexityzoo.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.cwerl.complexityzoo.model.data.para.ParaSubProblem;

@Repository
public interface ParaSubProblemRepository extends JpaRepository<ParaSubProblem, Long> {
    
}
