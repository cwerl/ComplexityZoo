package de.cwerl.complexityzoo.repository.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.cwerl.complexityzoo.model.data.Problem;
import de.cwerl.complexityzoo.model.data.normal.NormalProblem;
import de.cwerl.complexityzoo.model.data.para.ParaProblem;
import de.cwerl.complexityzoo.model.data.para.ParaSubProblem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    public NormalProblem getNormalById(long id);

    public ParaProblem getParaById(long id);

    @Query("SELECT p FROM Problem p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%')) ORDER BY LOWER(name)")
    public List<Problem> searchProblem(String q);

    @Query("SELECT p FROM Problem p WHERE p.type = 'NORMAL' OR p.type = 'PARAMETERIZED' ORDER BY LOWER(name)")
    public List<Problem> findAllOrdered();

    public Boolean existsByNameIgnoreCase(String name);

    public Problem findByNameIgnoreCase(String name);

    @Modifying
    @Query("UPDATE Problem p SET p.description = ?2 WHERE p.id = ?1")
    public void updateDescription(long id, String description);

    @Query("SELECT p FROM ParaSubProblem p WHERE p.parentProblem.id = ?1")
    public List<ParaSubProblem> getSubProblems(long id);

    @Query("SELECT p FROM ParaProblem p ORDER BY LOWER(name)")
    public List<ParaProblem> findAllParaOrdered();
}
