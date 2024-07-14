package com.bank.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private static final String[] AUTH_WHITE_LIST = {
            "/",
            "/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // other public endpoints
            "/h2-console/**",
            "/v2/api-docs/**",
            "/swagger-ui/**",
            "/v1/api-docs/**",
            "/swagger-resources/**",
            "/console/**",
            "/account/**"
    };
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        //http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .cors(cors -> cors.disable())
                //.csrf(csrf -> csrf.disable())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(fl -> fl.disable())
                .securityMatcher("/**")
                .authorizeHttpRequests(req -> req
                        .requestMatchers(AUTH_WHITE_LIST).permitAll())
                .securityMatcher("/**")
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/api/accounts/**").permitAll()

                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/h2/**").permitAll()
                        .requestMatchers("/api/test/admin/**").hasRole("USER")
                       // .requestMatchers("/api/test/user/**").hasRole("USER")
                        .requestMatchers("/api/test/user/**").hasAuthority("READ_PRIVILEGE")
                        //.requestMatchers("/api/test/admin/**").hasAuthority("SCOPE_READ_PRIVILEGE")
                        //hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .headers(req -> req.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());
        return authManagerBuilder.build();
    }
}
