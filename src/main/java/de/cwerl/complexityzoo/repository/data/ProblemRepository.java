package de.cwerl.complexityzoo.repository.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.cwerl.complexityzoo.model.data.normal.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query("SELECT p FROM Problem p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%')) ORDER BY LOWER(name)")
    public List<Problem> searchProblem(String q);

    @Query("SELECT p FROM Problem p ORDER BY LOWER(name)")
    public List<Problem> findAllOrdered();

    @Modifying
    @Query("UPDATE Problem p SET p.description = ?2 WHERE p.id = ?1")
    public void updateDescription(long id, String description);
}
