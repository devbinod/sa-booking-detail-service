package edu.miu590.bookingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    private JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        return http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterAfter(jwtTokenFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/auth/verify", "/actuator/**", "/", "/health").permitAll()
                .antMatchers("/api/bookings/v3/api-docs/**", "/api/bookings/configuration/ui", "/api/bookings/swagger-resources/**", "/api/bookings/configuration/security",//swagger
                        "/api/bookings/swagger-ui/**",
                        "/api/bookings/swagger/**",
                        "/api/bookings/swagger-resources/**",
                        "/api/bookings/webjars/**").permitAll()
                .antMatchers("/api/bookings/v3/api-docs/swagger-config","/api/bookings/swagger-ui.html").permitAll()
                .antMatchers("/api/bookings/swagger-ui/index.html").permitAll()
                .antMatchers("/**").authenticated()
                .anyRequest()
                .authenticated().and().build();
    }

}
