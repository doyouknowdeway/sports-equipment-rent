package com.doyouknowdeway.sportsequipmentrent.configuration;

import com.doyouknowdeway.sportsequipmentrent.filter.JwtTokenFilter;
import com.doyouknowdeway.sportsequipmentrent.mapper.ProfileMapper;
import com.doyouknowdeway.sportsequipmentrent.repository.ProfileRepository;
import com.doyouknowdeway.sportsequipmentrent.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilter jwtTokenFilter;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() {
        return new UserDetailsServiceImpl(profileRepository, profileMapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/oauth/**", "/register").permitAll()

                .antMatchers("/items", "/items/filtered").not().authenticated()
                .antMatchers("/items/**").hasRole("ADMIN")
                .antMatchers("/orders/create").not().authenticated()
                .antMatchers("/orders/{orderId}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/orders").hasRole("ADMIN")
                .antMatchers("/profiles").hasRole("ADMIN")
                .antMatchers("/profiles/**").hasAnyRole("USER", "ADMIN")
                .antMatchers().hasAnyRole("USER", "ADMIN")

                .anyRequest().not().authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized"))

                .and()
                .sessionManagement(sm -> sm.sessionCreationPolicy(STATELESS))
                .addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)

                .headers()
                .cacheControl();

    }

}
