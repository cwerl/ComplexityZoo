package de.cwerl.complexityzoo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.cwerl.complexityzoo.model.ComplexityClass;

public interface ComplexityClassRepository extends JpaRepository<ComplexityClass, Integer> {
    @Query("SELECT c FROM ComplexityClass c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<ComplexityClass> searchClass(String q);
}
