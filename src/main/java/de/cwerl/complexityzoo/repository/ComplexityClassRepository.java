package de.cwerl.complexityzoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.cwerl.complexityzoo.model.ComplexityClass;

@Repository
public interface ComplexityClassRepository extends JpaRepository<ComplexityClass, Integer> {
    @Query("SELECT c FROM ComplexityClass c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', ?1, '%')) ORDER BY LOWER(name)")
    public List<ComplexityClass> searchClass(String q);

    @Query("SELECT c FROM ComplexityClass c ORDER BY LOWER(name)")
    public List<ComplexityClass> findAllByOrderByNameAsc();

    public Boolean existsByNameIgnoreCase(String name);

    public ComplexityClass findByNameIgnoreCase(String name);

    public ComplexityClass findNameById(Integer id);
}
