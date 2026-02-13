package com.tecbom.e_commerce.Infra.Security.Filter;

import com.tecbom.e_commerce.Application.Ports.Output.MasterRepository;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;
import com.tecbom.e_commerce.Infra.Security.Service.TokenService;
import com.tecbom.e_commerce.Infra.Security.Details.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final MasterRepository masterRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository, MasterRepository masterRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.masterRepository = masterRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = this.recoverToken(request);

        if (token != null) {
            String cpfSubject = tokenService.validateToken(token);

            if (!cpfSubject.isEmpty()) {
                UserPrincipal userPrincipal;
                Cpf cpf = new Cpf(cpfSubject);

                var userDomain = userRepository.getUserByCpf(cpf);

                if (userDomain.isPresent()) {
                    userPrincipal = new UserPrincipal(userDomain.get());
                } else {
                    var masterDomain = masterRepository.findMasterByCpf(cpf)
                            .orElseThrow(() -> new UserNotFoundException());
                    userPrincipal = new UserPrincipal(masterDomain);
                }

                var authentication = new UsernamePasswordAuthenticationToken(
                        userPrincipal, null, userPrincipal.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.replace("Bearer ", "");
    }
}