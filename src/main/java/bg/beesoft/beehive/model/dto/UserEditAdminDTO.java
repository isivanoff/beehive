package bg.beesoft.beehive.model.dto;

import bg.beesoft.beehive.model.entity.UserRoleEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class UserEditAdminDTO {
    @NotNull
    private Long id;

    @NotEmpty
    @Email
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;


    private boolean isBanned;

    private boolean isActive;

    private String imageUrl;

    public List<Long> getUserRoleIds() {
        return userRoleIds;
    }

    public UserEditAdminDTO setUserRoleIds(List<Long> userRoleIds) {
        this.userRoleIds = userRoleIds;
        return this;
    }

    private List<Long> userRoleIds;

    public UserEditAdminDTO() {
    }

    public Long getId() {
        return id;
    }

    public UserEditAdminDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEditAdminDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEditAdminDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEditAdminDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public UserEditAdminDTO setBanned(boolean banned) {
        isBanned = banned;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserEditAdminDTO setActive(boolean active) {
        isActive = active;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserEditAdminDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

}
