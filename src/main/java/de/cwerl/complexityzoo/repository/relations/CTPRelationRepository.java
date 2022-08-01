package de.cwerl.complexityzoo.repository.relations;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import de.cwerl.complexityzoo.model.data.AbstractProblem;
import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.relations.CTPRelation.CTPRelation;

public interface CTPRelationRepository extends JpaRepository<CTPRelation, Long> {
    @Query("SELECT r FROM CTPRelation r WHERE r.complexityClass.id = ?1")
    public Set<CTPRelation> findRelationsByComplexityClass(long classId);

    @Query("SELECT r FROM CTPRelation r WHERE r.problem.id = ?1")
    public Set<CTPRelation> findRelationsByProblem(long problemId);
    
    @Query("SELECT r.problem FROM CTPRelation r WHERE r.complexityClass.id = ?1")
    public List<AbstractProblem> findProblemsByComplexityClass(long classId);

    @Query("SELECT r.complexityClass FROM CTPRelation r WHERE r.problem.id = ?1")
    public List<ComplexityClass> findComplexityClassesByProblem(long problemId);

    @Query("SELECT candidate FROM AbstractProblem candidate WHERE candidate.type = ?2 AND NOT candidate.id = ?1 AND NOT EXISTS (SELECT r FROM CTPRelation r WHERE r.complexityClass.id = ?1 AND r.problem.id = candidate.id) ORDER BY LOWER(name)")
    public List<AbstractProblem> findAllProblemCandidatesOrdered(long classId, String type);

    @Query("SELECT candidate FROM ComplexityClass candidate WHERE candidate.type = ?2 AND NOT candidate.id = ?1 AND NOT EXISTS (SELECT r FROM CTPRelation r WHERE r.complexityClass.id = candidate.id AND r.problem.id = ?1) ORDER BY LOWER(name)")
    public List<ComplexityClass> findAllComplexityClassCandidatesOrdered(long problemId, String type);

    @Modifying
    @Query("UPDATE CTPRelation r SET r.reference = ?2 WHERE r.id = ?1")
    public void updateReference(long id, String reference);
}
