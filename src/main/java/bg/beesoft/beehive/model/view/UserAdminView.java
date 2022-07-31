package bg.beesoft.beehive.model.view;

import bg.beesoft.beehive.model.entity.UserRoleEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class UserAdminView {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private boolean isBanned;

    private boolean isActive;

    private String imageUrl;

    LocalDateTime lastLoggedIn;

    private List<UserRoleEntity> userRoles;

    public UserAdminView() {
    }

    public Long getId() {
        return id;
    }

    public UserAdminView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserAdminView setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserAdminView setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserAdminView setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public UserAdminView setBanned(boolean banned) {
        isBanned = banned;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserAdminView setActive(boolean active) {
        isActive = active;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserAdminView setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocalDateTime getLastLoggedIn() {
        return lastLoggedIn;
    }

    public UserAdminView setLastLoggedIn(LocalDateTime lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
        return this;
    }

    public List<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public UserAdminView setUserRoles(List<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
        return this;
    }
}
