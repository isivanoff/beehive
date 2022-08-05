package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.dto.UserEditAdminDTO;
import bg.beesoft.beehive.model.dto.UserEditDTO;
import bg.beesoft.beehive.model.dto.UserRegisterDTO;
import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.model.entity.UserRoleEntity;
import bg.beesoft.beehive.model.entity.enums.UserRoleEnum;
import bg.beesoft.beehive.model.exception.NotFoundException;
import bg.beesoft.beehive.model.view.UserAdminView;
import bg.beesoft.beehive.model.view.UserEditAdminView;
import bg.beesoft.beehive.repository.UserRepository;
import bg.beesoft.beehive.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserDetailsService appUserDetailsService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private String adminPass;
    private UserRoleRepository userRoleRepository;
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder,
                       UserDetailsService appUserDetailsService,
                       @Value("${app.default.admin.password}") String adminPass, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailsService = appUserDetailsService;
        this.adminPass = adminPass;
        this.modelMapper = modelMapper;
    }

    public void init() {
        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);
            UserRoleEntity moderatorRole = new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR);

            adminRole = userRoleRepository.save(adminRole);
            moderatorRole = userRoleRepository.save(moderatorRole);

            initAdmin(List.of(adminRole, moderatorRole));
            initModerator(List.of(moderatorRole));
            initUser(List.of());
        }
    }

    private void initAdmin(List<UserRoleEntity> roles) {
        UserEntity admin = new UserEntity().
                setUserRoles(roles).
                setFirstName("Admin").
                setLastName("Adminov").
                setActive(true).

                setEmail("admin@example.com").
                setPassword(passwordEncoder.encode(adminPass));

        userRepository.save(admin);
    }

    private void initModerator(List<UserRoleEntity> roles) {
        UserEntity moderator = new UserEntity().
                setUserRoles(roles).
                setFirstName("Moderator").
                setLastName("Moderatorov").
                setActive(true).

                setEmail("moderator@example.com").
                setPassword(passwordEncoder.encode(adminPass));

        userRepository.save(moderator);
    }

    private void initUser(List<UserRoleEntity> roles) {
        UserEntity user = new UserEntity().
                setUserRoles(roles).
                setFirstName("User").
                setLastName("Userov").
                setActive(true).
                setEmail("user@example.com").
                setPassword(passwordEncoder.encode(adminPass));

        userRepository.save(user);
    }

    public void registerAndLogin(UserRegisterDTO userRegisterDTO) {
        UserEntity newUser =
                new UserEntity().
                        setEmail(userRegisterDTO.getEmail()).
                        setFirstName(userRegisterDTO.getFirstName()).
                        setLastName(userRegisterDTO.getLastName()).
                        setActive(true).
                        setLastLoggedIn(LocalDateTime.now()).
                        setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        userRepository.save(newUser);

        updateAuthentication(newUser);
    }

    private void updateAuthentication(UserEntity newUser) {
        UserDetails userDetails =
                appUserDetailsService.loadUserByUsername(newUser.getEmail());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);
    }

    public UserEntity findByEmail(String name) {
        return userRepository.findByEmail(name).orElseThrow(() -> new NotFoundException("Невалиден акаунт."));
    }

    public UserEditDTO getEditDetails(String username) {
        return modelMapper.map(findByEmail(username), UserEditDTO.class);
    }

    public void update(UserEditDTO userEditDTO) {
        UserEntity newUser = findByEmail(userEditDTO.getEmail())
                .setFirstName(userEditDTO.getFirstName())
                .setLastName(userEditDTO.getLastName())
                .setImageUrl(userEditDTO.getImageUrl());
        userRepository.save(newUser);
        updateAuthentication(newUser);
    }

    public void deleteByEmail(String username) {
        userRepository.deleteByEmail(username);
        SecurityContextHolder.clearContext();
    }

    public void updatePassword(String email, String newPassword) {
        UserEntity userEntity = findByEmail(email);
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
        updateAuthentication(userEntity);
    }

    public boolean emailIsTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String email = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();
        userRepository.save(findByEmail(email).setLastLoggedIn(LocalDateTime.now()));
    }


    public boolean isBlocked(String userName) {
        UserEntity userEntity = findByEmail(userName);
        return userEntity.isBanned() || !userEntity.isActive();
    }

    @Scheduled(cron = "@monthly")   //(cron = "* * * 1 * *")
    public void scheduleInactiveUserDeactivation() {
        LocalDateTime current_timestamp = LocalDateTime.now();

        userRepository.findAll().
                forEach(user -> {
                    long months = ChronoUnit.MONTHS.between(user.getLastLoggedIn(), current_timestamp);
                    if (months > 3 && user.isActive() && !user.getUserRoles().contains(UserRoleEnum.ADMIN)) {
                        user.setActive(false);
                        userRepository.save(user);
                    }
                });
    }

    public List<UserAdminView> findAllUsersAdminView() {
        return userRepository.findAll()
                .stream()
                .map(user->modelMapper.map(user,UserAdminView.class))
                .collect(Collectors.toList());
    }

    public UserEditAdminDTO findUserAdminEditById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Невалиден потребител"));
        UserEditAdminDTO userEditAdminDTO = new UserEditAdminDTO();
        userEditAdminDTO.
                setIsActive(userEntity.isActive()).
                setIsBanned(userEntity.isBanned()).
                setUserRoles(userEntity.getUserRoles().stream().map(e->e.getUserRole()).collect(Collectors.toList())) ;
        return userEditAdminDTO;
    }

    public void updateAdminDTO(UserEditAdminDTO userEditAdminDTO, Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()->new NotFoundException("Невалиден потребител."));
        userEntity.setActive(!userEditAdminDTO.isActive())
                .setBanned(userEditAdminDTO.isBanned())
                .setUserRoles(
                        userEditAdminDTO.getUserRoles().stream().map(
                                role -> userRoleRepository.findByUserRole(role)
                        ).collect(Collectors.toList())
                );
        userRepository.save(userEntity);
    }

    public UserEditAdminView findUserViewById(Long id) {
     return modelMapper.map(findById(id),UserEditAdminView.class);

    }

    public UserEntity findById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("Невалиден потребител."));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
