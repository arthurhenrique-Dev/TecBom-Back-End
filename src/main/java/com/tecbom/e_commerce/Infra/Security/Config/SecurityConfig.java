package com.tecbom.e_commerce.Infra.Security.Config;

import com.tecbom.e_commerce.Infra.Security.Filter.SecurityFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(SecurityFilter securityFilter, AuthenticationConfiguration authenticationConfiguration) {
        this.securityFilter = securityFilter;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .cors(cors -> cors.configure(http))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/TecBom/admin_dashboard/master").hasRole("MASTER")
                        .requestMatchers(HttpMethod.PUT, "/TecBom/admin_dashboard/master").hasRole("MASTER")
                        .requestMatchers(HttpMethod.DELETE, "/TecBom/admin_dashboard/user/stats").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/TecBom/admin_dashboard/user").hasRole("MASTER")
                        .requestMatchers(HttpMethod.PUT, "/TecBom/admin_dashboard/user").hasRole("MASTER")
                        .requestMatchers(HttpMethod.POST, "/TecBom/admin_dashboard/user/stats").hasRole("MASTER")
                        .requestMatchers(HttpMethod.GET, "/TecBom/admin_dashboard/user").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/TecBom/admin_dashboard/admin").hasRole("MASTER")
                        .requestMatchers(HttpMethod.POST, "/TecBom/auth/register/admin").hasRole("MASTER")
                        .requestMatchers(HttpMethod.POST, "/TecBom/auth/register/master").hasRole("MASTER")
                        .requestMatchers(HttpMethod.GET, "/TecBom/Shop/admin/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/TecBom/Shop/admin/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/TecBom/Shop/admin/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/TecBom/Shop/admin/products/purchase").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/TecBom/Shop/admin/products/discount").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/TecBom/Shop/admin/products/model").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/TecBom/Shop/admin/products/model").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/TecBom/user/cart/add").hasRole("COMUM")
                        .requestMatchers(HttpMethod.DELETE, "/TecBom/user/cart/remove").hasRole("COMUM")
                        .requestMatchers(HttpMethod.DELETE, "/TecBom/user/configuration/deactivate").hasRole("COMUM")
                        .requestMatchers(HttpMethod.PUT, "/TecBom/user/configuration/update").hasRole("COMUM")
                        .requestMatchers(HttpMethod.GET, "/TecBom/user/cart").hasRole("COMUM")
                        .anyRequest().permitAll())
                .addFilterBefore(securityFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
