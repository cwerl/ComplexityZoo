package de.cwerl.complexityzoo.repository.relations;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query("SELECT p FROM Problem p WHERE NOT EXISTS (SELECT r FROM CTPRelation r WHERE (r.problem.id = p.id AND r.complexityClass.id = ?1)) ORDER BY LOWER(name)")
    public List<Problem> findAllProblemCandidatesOrdered(long classId);

    @Query("SELECT c FROM ComplexityClass c WHERE NOT EXISTS (SELECT r FROM CTPRelation r WHERE (r.complexityClass.id = c.id AND r.problem.id = ?1)) ORDER BY LOWER(name)")
    public List<ComplexityClass> findAllComplexityClassCandidatesOrdered(long problemId);
}
