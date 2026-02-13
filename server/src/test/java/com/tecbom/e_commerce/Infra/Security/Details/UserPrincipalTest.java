package com.tecbom.e_commerce.Infra.Security.Details;

import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.Entities.Users.Role;
import com.tecbom.e_commerce.Domain.Entities.Users.Status;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserPrincipalTest {

    private ReferenceObject references;

    @BeforeEach
    void setUp() {
        references = new ReferenceObject();
    }

    @Test
    void deveCriarUserPrincipalAPartirDeUserComRoleComum() {
        User user = references.NormalUser();
        UserPrincipal principal = new UserPrincipal(user);

        assertEquals(user.getCpf().toString(), principal.getUsername());
        assertEquals(user.getPassword().password(), principal.getPassword());
        assertTrue(principal.isEnabled());

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("COMUM")));
    }

    @Test
    void deveCriarUserPrincipalAPartirDeMasterComTodasAsPermissoes() {
        Master master = references.NormalMaster();
        UserPrincipal principal = new UserPrincipal(master);

        assertEquals(master.getCpf().toString(), principal.getUsername());
        assertEquals(master.getPassword().password(), principal.getPassword());
        assertTrue(principal.isEnabled());

        List<String> auths = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        assertEquals(3, auths.size());
        assertTrue(auths.containsAll(List.of("MASTER", "ADMIN", "COMUM")));
    }

    @Test
    void deveRetornarDisabledQuandoStatusForOff() {
        User userBase = references.NormalUser();
        User userOff = new User(
                userBase.getCpf(),
                userBase.getName(),
                userBase.getPassword(),
                userBase.getEmail(),
                userBase.getAddress(),
                userBase.getPhoneNumber(),
                userBase.getRole(),
                Status.OFF,
                userBase.getEmailValidation(),
                userBase.getPasswordUpdater()
        );

        UserPrincipal principal = new UserPrincipal(userOff);

        assertFalse(principal.isEnabled());
    }

    @Test
    void deveValidarHierarquiaDeAdmin() {
        User userBase = references.NormalUser();
        User adminUser = new User(
                userBase.getCpf(),
                userBase.getName(),
                userBase.getPassword(),
                userBase.getEmail(),
                userBase.getAddress(),
                userBase.getPhoneNumber(),
                Role.ADMIN,
                Status.ON,
                userBase.getEmailValidation(),
                userBase.getPasswordUpdater()
        );

        UserPrincipal principal = new UserPrincipal(adminUser);

        List<String> auths = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        assertEquals(2, auths.size());
        assertTrue(auths.contains("ADMIN"));
        assertTrue(auths.contains("COMUM"));
        assertFalse(auths.contains("MASTER"));
    }
}