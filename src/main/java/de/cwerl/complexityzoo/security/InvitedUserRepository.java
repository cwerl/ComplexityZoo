package de.cwerl.complexityzoo.security;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitedUserRepository extends CrudRepository<InvitedUser, String> {
    InvitedUser findByEmail(String email);
}
