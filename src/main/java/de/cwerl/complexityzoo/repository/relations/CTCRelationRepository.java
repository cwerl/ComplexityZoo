package de.cwerl.complexityzoo.repository.relations;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.cwerl.complexityzoo.model.data.ComplexityClass;
import de.cwerl.complexityzoo.model.relations.CTCRelation;

@Repository
public interface CTCRelationRepository extends JpaRepository<CTCRelation, Long> {

    @Query("SELECT r FROM CTCRelation r WHERE r.firstClass.id = ?1 OR r.secondClass.id = ?1")
    public Set<CTCRelation> findRelationsByComplexityClass(long classId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM CTCRelation r WHERE (r.firstClass.id = ?1 AND r.secondClass.id = ?2) OR (r.firstClass.id = ?2 AND r.secondClass.id = ?1)")
    public boolean existsByClassPair(long firstClassId, long secondClassId);

    @Query("SELECT candidate FROM ComplexityClass candidate WHERE NOT candidate.id = ?1 AND NOT EXISTS (SELECT r FROM CTCRelation r WHERE (r.firstClass.id = ?1 AND r.secondClass.id = candidate.id) OR (r.firstClass.id = candidate.id AND r.secondClass.id = ?1)) ORDER BY LOWER(name)")
    public List<ComplexityClass> findAllRelationCandidatesOrdered(long classId);
}
