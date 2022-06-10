package bg.beesoft.beehive.model.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginDTO {

    @NotNull
    @Email
    @Column(nullable = false,unique = true)
    private String email;

    @NotNull
    @Size(min = 5,max = 20)
    @Column(nullable = false,unique = true)
    private String password;

    public String getEmail() {
        return email;
    }

    public UserLoginDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "UserLoginDTO{" +
                "username='" + email + '\'' +
                ", password='" + (password != null ? "[PROVIDED]" : null) + '\'' +
                '}';
    }
}
