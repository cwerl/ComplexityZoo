package de.cwerl.complexityzoo.repository.data;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import de.cwerl.complexityzoo.model.data.para.Parameterization;

public interface ParameterizationRepository extends JpaRepository<Parameterization, Long> {
    
    @Query("SELECT para FROM Parameterization para WHERE para.parent.id = ?1")
    public Set<Parameterization> getAllByProblemId(long problemId);

    @Modifying
    @Query("UPDATE Parameterization para SET para.description = ?2 WHERE para.id = ?1")
    public void updateDescription(long id, String description);
}
