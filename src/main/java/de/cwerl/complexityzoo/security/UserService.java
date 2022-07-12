package de.cwerl.complexityzoo.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvitedUserRepository invitedUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistsException, UserNotInvitedException {
        if(userExists(userDto.getUsername(), userDto.getEmail())) {
            throw new UserAlreadyExistsException("User with this username or email already exists.");
        } else if(!userIsInvited(userDto.getEmail())) {
            throw new UserNotInvitedException("User with email " + userDto.getEmail() + " hasn't been invited yet.");
        }
        final User user = new User();
        invitedUserRepository.deleteById(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    public InvitedUser inviteUser(String email) throws UserAlreadyExistsException {
        if(userExists(email) || userIsInvited(email)) {
            throw new UserAlreadyExistsException("User with this email already exists or is already invited.");
        }
        final InvitedUser user = new InvitedUser();
        user.setEmail(email);
        return invitedUserRepository.save(user);
    }

    private boolean userExists(String username, String email) {
        return userRepository.findByUsername(username) != null || userRepository.findByEmail(email) != null;
    }

    private boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean userIsInvited(String email) {
        return invitedUserRepository.findByEmail(email) != null;
    }
}
