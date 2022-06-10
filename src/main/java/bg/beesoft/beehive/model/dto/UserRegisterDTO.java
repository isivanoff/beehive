package bg.beesoft.beehive.model.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegisterDTO {

    @NotEmpty
    @Email
    @Column(nullable = false,unique = true)
    private String email;
    @NotEmpty
    @Size(min=2, max=20)
    @Column(nullable = false)
    private String firstName;

    @NotEmpty
    @Size(min=2, max=20)
    @Column(nullable = false)
    private String lastName;

    @NotEmpty
    @Size(min=5, max=20)
    @Column(nullable = false)
    private String password;

    @NotEmpty
    @Size(min=5, max=20)
    @Column(nullable = false)
    private String confirmPassword;

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
