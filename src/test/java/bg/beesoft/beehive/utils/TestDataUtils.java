package bg.beesoft.beehive.utils;

import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.model.entity.UserRoleEntity;
import bg.beesoft.beehive.model.entity.enums.UserRoleEnum;
import bg.beesoft.beehive.repository.UserRepository;
import bg.beesoft.beehive.repository.UserRoleRepository;
import org.springframework.stereotype.Component;

@Component
public class TestDataUtils {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    public TestDataUtils(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);
            UserRoleEntity moderatorRole = new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR);

            userRoleRepository.save(adminRole);
            userRoleRepository.save(moderatorRole);
        }
    }

    public UserEntity createTestAdmin(String email) {

        initRoles();

        var admin = new UserEntity().
                setEmail(email).
                setFirstName("Admin").
                setLastName("Adminov").
                setActive(true).
                setUserRoles(userRoleRepository.findAll())
                .setPassword("123456");

        return userRepository.save(admin);
    }

    public UserEntity createTestUser(String email) {

        initRoles();

        var user = new UserEntity().
                setEmail(email).
                setFirstName("User").
                setLastName("Userov").
                setActive(true);
        return userRepository.save(user);
    }

    public void cleanUpDatabase() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

}
