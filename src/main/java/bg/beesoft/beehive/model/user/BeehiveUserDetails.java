package bg.beesoft.beehive.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BeehiveUserDetails implements UserDetails {
    private final String password;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final boolean enabled;
    private final Collection<GrantedAuthority> authorities;
    private final boolean isBanned;


    public BeehiveUserDetails(String password,
                              String username,
                              String firstName,
                              String lastName,
                              boolean enabled,
                              Collection<GrantedAuthority> authorities,
                              boolean isBanned) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
        this.enabled = enabled;
        this.isBanned=isBanned;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        ArrayList<String> allNames = new ArrayList<String>(List.of(getFirstName(), getLastName()));
        return allNames.stream().filter(e -> !e.equals("")).collect(Collectors.joining(" "));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBanned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }



}
