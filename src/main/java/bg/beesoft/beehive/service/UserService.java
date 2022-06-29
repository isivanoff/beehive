package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.UserLoginDTO;
import bg.beesoft.beehive.model.dto.UserRegisterDTO;
import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.model.user.CurrentUser;
import bg.beesoft.beehive.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CurrentUser currentUser;
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CurrentUser currentUser, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
    }

    public boolean login(UserLoginDTO userLoginDTO) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(userLoginDTO.getEmail());

        if (userOpt.isEmpty() || !userOpt.get().isActive()) {
            return false;
        }

        String enteredPassword = userLoginDTO.getPassword();
        String actualPassword = userOpt.get().getPassword();

        boolean correctPassword = passwordEncoder.matches(enteredPassword, actualPassword);

        if (correctPassword) {
            login(userOpt.get());
        } else {
            logout();
        }

        return correctPassword;
    }

    private void login(UserEntity userEntity) {
        currentUser
                .setLoggedIn(true)
                .setName(userEntity.getFirstName() + " " + userEntity.getLastName())
                .setId(userEntity.getId());
    }

    public void logout() {
        currentUser.clear();
    }

    public void registerAndLogin(UserRegisterDTO userRegisterDTO) {
        userRegisterDTO.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        UserEntity newUser = modelMapper.map(userRegisterDTO, UserEntity.class);
        newUser.setActive(true);
        newUser = userRepository.save(newUser);
//TODO: Check if email already exists, check if the passwords match

        login(newUser);
    }

    public Optional<UserEntity> findByUsernameAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, passwordEncoder.encode(password));
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
