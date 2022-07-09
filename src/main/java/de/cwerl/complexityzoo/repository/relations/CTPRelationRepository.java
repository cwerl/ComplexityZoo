package de.cwerl.complexityzoo.repository.relations;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.cwerl.complexityzoo.model.data.AbstractProblem;
import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.data.normal.Problem;
import de.cwerl.complexityzoo.model.data.para.Parameterization;
import de.cwerl.complexityzoo.model.relations.CTPRelation;

public interface CTPRelationRepository extends JpaRepository<CTPRelation, Long> {
    @Query("SELECT r FROM CTPRelation r WHERE r.complexityClass.id = ?1")
    public Set<CTPRelation> findRelationsByComplexityClass(long classId);

    @Query("SELECT r FROM CTPRelation r WHERE r.problem.id = ?1")
    public Set<CTPRelation> findRelationsByProblem(long problemId);
    
    @Query("SELECT r.problem FROM CTPRelation r WHERE r.complexityClass.id = ?1")
    public List<AbstractProblem> findProblemsByComplexityClass(long classId);

    @Query("SELECT r.complexityClass FROM CTPRelation r WHERE r.problem.id = ?1")
    public List<ComplexityClass> findComplexityClassesByProblem(long problemId);

    @Query("SELECT candidate FROM Parameterization candidate WHERE NOT candidate.id = ?1 AND NOT EXISTS (SELECT r FROM CTPRelation r WHERE r.complexityClass.id = ?1 AND r.problem.id = candidate.id) ORDER BY LOWER(name)")
    public List<Parameterization> findAllParaProblemCandidatesOrdered(long classId);

    @Query("SELECT candidate FROM Problem candidate WHERE NOT candidate.id = ?1 AND NOT EXISTS (SELECT r FROM CTPRelation r WHERE r.complexityClass.id = ?1 AND r.problem.id = candidate.id) ORDER BY LOWER(name)")
    public List<Problem> findAllProblemCandidatesOrdered(long classId);

    @Query("SELECT candidate FROM ParaComplexityClass candidate WHERE NOT candidate.id = ?1 AND NOT EXISTS (SELECT r FROM CTPRelation r WHERE r.complexityClass.id = candidate.id AND r.problem.id = ?1) ORDER BY LOWER(name)")
    public List<ComplexityClass> findAllParaComplexityClassCandidatesOrdered(long problemId);

    @Query("SELECT candidate FROM NormalComplexityClass candidate WHERE NOT candidate.id = ?1 AND NOT EXISTS (SELECT r FROM CTPRelation r WHERE r.complexityClass.id = candidate.id AND r.problem.id = ?1) ORDER BY LOWER(name)")
    public List<ComplexityClass> findAllComplexityClassCandidatesOrdered(long problemId);

    
}
