package com.Cinema.security;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = passwordEncoder();

        // Authentification en utilisant inMemoryAuthentication

        // auth.inMemoryAuthentication().withUser("Hamid").password(encoder.encode("uzumaki123")).roles("USER");
        // auth.inMemoryAuthentication().withUser("Lektef").password(encoder.encode("uzumaki123")).roles("USER");
        // auth.inMemoryAuthentication().withUser("Zrire9").password(encoder.encode("uzumaki123")).roles("USER",
        // "ADMIN");

        // Authentification en utilisant jdbcAuthentication()
        // System.out.println("********************************************");
        // System.out.println(encoder.encode("uzumaki123"));
        // System.out.println("********************************************");
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username as principal, password as credentials, active "
                        + "FROM users WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username as principal, role FROM users_roles WHERE username = ?")
                .passwordEncoder(passwordEncoder()).rolePrefix("ROLE_");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login");
        http.authorizeRequests().antMatchers("/user/**","/login", "/logout",
        "/webjars/**").permitAll();
        http.authorizeRequests().antMatchers("/dashboard/*").hasRole("ADMIN");
        // http.authorizeRequests().antMatchers("/villes/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        // http.cors().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("content-type", "authorization", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
