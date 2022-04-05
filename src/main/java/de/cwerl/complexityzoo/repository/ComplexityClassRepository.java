package de.cwerl.complexityzoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.cwerl.complexityzoo.model.ComplexityClass;

public interface ComplexityClassRepository extends JpaRepository<ComplexityClass, Integer> {
    
}
