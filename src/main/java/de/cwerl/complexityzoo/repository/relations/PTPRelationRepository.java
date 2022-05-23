package de.cwerl.complexityzoo.repository.relations;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.cwerl.complexityzoo.model.Problem;
import de.cwerl.complexityzoo.model.relations.PTPRelation;

@Repository
public interface PTPRelationRepository extends JpaRepository<PTPRelation, Long> {
    
    @Query("SELECT r FROM PTPRelation r WHERE r.firstProblem.id = ?1")
    public Set<PTPRelation> findRelationsByProblem(long problemId);

    @Query("SELECT DISTINCT r.type FROM PTPRelation r")
    public Set<String> findAllTypes();

    @Query("SELECT p FROM Problem p WHERE NOT p.id = ?1 ORDER BY LOWER(name)")
    public List<Problem> findAllRelationCandidatesOrdered(long problemId);
}
