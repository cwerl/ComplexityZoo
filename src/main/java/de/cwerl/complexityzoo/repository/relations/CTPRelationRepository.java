package de.cwerl.complexityzoo.repository.relations;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.Problem;
import de.cwerl.complexityzoo.model.relations.CTPRelation;

public interface CTPRelationRepository extends JpaRepository<CTPRelation, Long> {
    @Query("SELECT r FROM CTPRelation r WHERE r.complexityClass.id = ?1")
    public Set<CTPRelation> findRelationsByComplexityClass(long classId);

    @Query("SELECT r FROM CTPRelation r WHERE r.problem.id = ?1")
    public Set<CTPRelation> findRelationsByProblem(long problemId);
    
    @Query("SELECT r.problem FROM CTPRelation r WHERE r.complexityClass.id = ?1")
    public List<Problem> findProblemsByComplexityClass(long classId);

    @Query("SELECT r.complexityClass FROM CTPRelation r WHERE r.problem.id = ?1")
    public List<ComplexityClass> findComplexityClassesByProblem(long problemId);

    @Query("SELECT candidate FROM Problem candidate WHERE NOT candidate.id = :#{#theClass.id} AND NOT EXISTS (SELECT r FROM CTPRelation r WHERE r.complexityClass.id = :#{#theClass.id} AND r.problem.id = candidate.id) ORDER BY LOWER(name)")
    public List<Problem> findAllProblemCandidatesOrdered(@Param("theClass") ComplexityClass theClass);

    @Query("SELECT candidate FROM ComplexityClass candidate WHERE NOT candidate.id = :#{#theProblem.id} AND NOT EXISTS (SELECT r FROM CTPRelation r WHERE r.complexityClass.id = candidate.id AND r.problem.id = :#{#theProblem.id}) ORDER BY LOWER(name)")
    public List<ComplexityClass> findAllComplexityClassCandidatesOrdered(@Param("theProblem") Problem theProblem);

    
}
