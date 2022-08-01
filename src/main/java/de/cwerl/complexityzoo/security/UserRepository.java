package de.cwerl.complexityzoo.security;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);

    User findByEmail(String email);
}
