package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.model.user.BeehiveUserDetails;
import bg.beesoft.beehive.utils.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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

    @BeforeEach
    void setUp(){
        UserEntity admin = testDataUtils.createTestAdmin("mockAdmin@example.com");
     userDetails = new BeehiveUserDetails(
             admin.getPassword(),
             admin.getEmail(),
             admin.getFirstName(),
             admin.getLastName(),
             admin.isActive(),
             new ArrayList<>(),
             admin.isBanned()

     );
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testAdminUsersPageShown() throws Exception {
        mockMvc.perform(get("/admin/users").with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("users"));
    }

}
