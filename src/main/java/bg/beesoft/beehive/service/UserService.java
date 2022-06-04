package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.UserLoginDTO;
import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.model.user.CurrentUser;
import bg.beesoft.beehive.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CurrentUser currentUser;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
    }

    public boolean login(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(userLoginDTO.getEmail());

        if (userOpt.isEmpty()) {
            return false;
        }

        String enteredPassword = userLoginDTO.getPassword();
        String actualPassword = userOpt.get().getPassword();

        boolean correctPassword = passwordEncoder.matches(enteredPassword, actualPassword);

        if (correctPassword) {
            currentUser.setLoggedIn(true).setName(userOpt.get().getFirstName() + " " + userOpt.get().getLastName());
        } else {
            logout();
        }

        return correctPassword;
    }

    public void logout() {
        currentUser.clear();
    }
}
