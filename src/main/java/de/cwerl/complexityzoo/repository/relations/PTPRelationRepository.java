package de.cwerl.complexityzoo.repository.relations;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.cwerl.complexityzoo.model.data.AbstractProblem;
import de.cwerl.complexityzoo.model.relations.PTPRelation.PTPRelation;

@Repository
public interface PTPRelationRepository extends JpaRepository<PTPRelation, Long> {
    
    @Query("SELECT r FROM PTPRelation r WHERE r.firstProblem.id = ?1")
    public Set<PTPRelation> findRelationsByProblem(long problemId);

    @Query("SELECT DISTINCT r.relationType FROM PTPRelation r")
    public Set<String> findAllTypes();

    @Query("SELECT candidate FROM AbstractProblem candidate WHERE NOT candidate.id = ?1 ORDER BY LOWER(name)")
    public List<AbstractProblem> findAllRelationCandidatesOrdered(long problemId);

    @Modifying
    @Query("UPDATE PTPRelation r SET r.reference = ?2 WHERE r.id = ?1")
    public void updateReference(long id, String reference);
}
