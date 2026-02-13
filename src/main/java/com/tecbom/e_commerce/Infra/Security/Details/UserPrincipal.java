package com.tecbom.e_commerce.Infra.Security.Details;

import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.Entities.Users.Role;
import com.tecbom.e_commerce.Domain.Entities.Users.Status;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final String cpf;
    private final String password;
    private final Role role;
    private final Status status;
    private final Object domainObject;

    public UserPrincipal(User user) {
        this.cpf = user.getCpf().toString();
        this.password = user.getPassword().password();
        this.role = user.getRole();
        this.status = user.getStatus();
        this.domainObject = user;
    }

    public UserPrincipal(Master master) {
        this.cpf = master.getCpf().toString();
        this.password = master.getPassword().password();
        this.role = Role.MASTER;
        this.status = master.getStatus();
        this.domainObject = master;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (role) {
            case MASTER -> List.of(
                    new SimpleGrantedAuthority("MASTER"),
                    new SimpleGrantedAuthority("ADMIN"),
                    new SimpleGrantedAuthority("COMUM")
            );
            case ADMIN -> List.of(
                    new SimpleGrantedAuthority("ADMIN"),
                    new SimpleGrantedAuthority("COMUM")
            );
            case COMUM -> List.of(
                    new SimpleGrantedAuthority("COMUM")
            );
        };
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return status == Status.ON;
    }
}
