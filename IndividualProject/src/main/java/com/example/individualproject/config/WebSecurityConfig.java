package com.example.individualproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select Email, password, active from users where Email =?")
                .authoritiesByUsernameQuery("select u.Email, ur.roles from users u inner join user_role ur on u.id_user = ur.user_id where u.Email=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/registration").permitAll()
                .antMatchers("/allUsers").hasAuthority("ADMIN")
                .antMatchers("/allReservs").hasAuthority("SOTRUDNIK")
                .antMatchers("/allReviews").hasAuthority("USER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    for (GrantedAuthority authority : authentication.getAuthorities()) {
                        if ("ADMIN".equals(authority.getAuthority())) {
                            response.sendRedirect("/allUsers");
                            return;
                        } else if ("SOTRUDNIK".equals(authority.getAuthority())) {
                            response.sendRedirect("/allReservServ");
                            return;
                        } else {
                            response.sendRedirect("/allReviews");
                            return;
                        }
                    }
                    response.sendRedirect("/");
                })
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable()
                .cors().disable();
    }


}