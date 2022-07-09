package de.cwerl.complexityzoo.repository.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.cwerl.complexityzoo.model.data.ComplexityClass;

@Repository
public interface ComplexityClassRepository extends JpaRepository<ComplexityClass, Long> {
    @Query("SELECT c FROM ComplexityClass c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', ?1, '%')) ORDER BY LOWER(name)")
    public List<ComplexityClass> searchClass(String q);

    @Query("SELECT c FROM ComplexityClass c ORDER BY LOWER(name)")
    public List<ComplexityClass> findAllOrdered();

    @Modifying
    @Query("UPDATE ComplexityClass c SET c.description = ?2 WHERE c.id = ?1")
    public void updateDescription(long id, String description);
}
