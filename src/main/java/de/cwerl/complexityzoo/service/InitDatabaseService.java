package de.cwerl.complexityzoo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.cwerl.complexityzoo.security.User;
import de.cwerl.complexityzoo.security.UserRepository;

@Service
public class InitDatabaseService {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public void init() {
        if(userRepository.count() == 0) {
            User admin = new User("admin", "cwerl@outlook.de", encoder.encode("r72tXz8!QbpY41G$7%Rx"));
            userRepository.save(admin);
        }
    }
}
