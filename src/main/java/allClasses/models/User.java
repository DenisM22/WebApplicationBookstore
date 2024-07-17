package allClasses.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class User implements UserDetails {
    private Long id;

    @NotBlank(message = "Имя пользователя не должно быть пустым")
    @Size(min = 3, max = 20, message = "Имя пользователя должно быть от 2 до 50 символов")
    private String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 5, message = "Пароль должен быть минимум 5 символов")
    private String password;
    private String role;

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

}
