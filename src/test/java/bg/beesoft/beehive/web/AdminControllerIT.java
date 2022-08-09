package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.model.user.BeehiveUserDetails;
import bg.beesoft.beehive.utils.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    private BeehiveUserDetails userDetails;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        UserEntity admin = testDataUtils.createTestAdmin("mockAdmin@example.com");
        userDetails = new BeehiveUserDetails(
                admin.getPassword(),
                admin.getEmail(),
                admin.getFirstName(),
                admin.getLastName(),
                admin.isActive(),
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")),
                admin.isBanned()
        );

        testUser = testDataUtils.createTestUser("mockUser@example.com");
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testAdminEditUserPageShown() throws Exception {
        mockMvc.perform(get("/admin/users/edit/{id}", testUser.getId()).with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-user-edit"));
    }

    @Test
    @WithUserDetails(value = "admin@example.com")
    void testAdminUsersPageShown() throws Exception {
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"));
    }


    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/admin/delete/{id}", testUser.getId())
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/users"));
    }

}
