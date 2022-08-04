package bg.beesoft.beehive.model.dto;

import bg.beesoft.beehive.model.entity.enums.UserRoleEnum;

import java.util.List;

public class UserEditAdminDTO {

    private boolean isBanned;

    private boolean isActive;


    private List<UserRoleEnum> userRoles;

    public List<UserRoleEnum> getUserRoles() {
        return userRoles;
    }

    public UserEditAdminDTO setUserRoles(List<UserRoleEnum> userRoles) {
        this.userRoles = userRoles;
        return this;
    }


    public UserEditAdminDTO() {
    }


    public boolean isBanned() {
        return isBanned;
    }

    public UserEditAdminDTO setIsBanned(boolean banned) {
        isBanned = banned;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserEditAdminDTO setIsActive(boolean active) {
        isActive = active;
        return this;
    }


}
