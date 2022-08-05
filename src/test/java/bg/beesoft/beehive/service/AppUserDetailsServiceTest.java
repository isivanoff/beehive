package bg.beesoft.beehive.service;

import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.model.entity.UserRoleEntity;
import bg.beesoft.beehive.model.entity.enums.UserRoleEnum;
import bg.beesoft.beehive.model.user.BeehiveUserDetails;
import bg.beesoft.beehive.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailsServiceTest {

        @Mock
        private UserRepository mockUserRepo;

        private bg.beesoft.beehive.service.AppUserDetailsService toTest;

        @BeforeEach
        void setUp() {
            toTest = new bg.beesoft.beehive.service.AppUserDetailsService(
                    mockUserRepo
            );
        }

    @Test
    void testLoadUserByUsername_UserExists() {
        UserEntity testUserEntity =
                new UserEntity().
                        setEmail("test@example.com").
                        setPassword("123456").
                        setFirstName("Sasho").
                        setLastName("Kolev").
                        setActive(true).

                        setImageUrl("http://profile.com/test").
                        setUserRoles(
                                List.of(
                                        new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN)
                                )
                        );

        when(mockUserRepo.findByEmail(testUserEntity.getEmail())).
                thenReturn(Optional.of(testUserEntity));

        BeehiveUserDetails userDetails = (BeehiveUserDetails)
                toTest.loadUserByUsername(testUserEntity.getEmail());

        Assertions.assertEquals(testUserEntity.getEmail(),
                userDetails.getUsername());
        Assertions.assertEquals(testUserEntity.getFirstName(), userDetails.getFirstName());
        Assertions.assertEquals(testUserEntity.getLastName(), userDetails.getLastName());
        Assertions.assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(testUserEntity.getFirstName() + " " + testUserEntity.getLastName(),
                userDetails.getFullName());


        var authorities = userDetails.getAuthorities();

        Assertions.assertEquals(1, authorities.size());

        var authoritiesIter = authorities.iterator();

        Assertions.assertEquals("ROLE_" + UserRoleEnum.ADMIN.name(),
                authoritiesIter.next().getAuthority());

    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("notfound@example.com")
        );
    }

    }

