package com.kucp1127.KidsLearningApp.Security_Configuration.SecurityConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private  JwtFilter jwtFilter;


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/register",
                                        "/login",
                                        "/childProfile",
                                        "/childProfile/{username}",
                                        "/find/{username}",
                                        "/practice/grade/{grade}",
                                        "/practice/grade/{grade}/category/{category}",
                                        "/practice/grade/{grade}/category/{category}/subCategory/{subCategory}",
                                        "/child/{id}",
                                        "/leaderboard/user/{username}/{category}",
                                        "/leaderboard/average/{username}/{category}",
                                        "/leaderboard/user/{username}/score",
                                        "/generate-roadmap/{username}",
                                        "/leaderboard/accuracy/{username}",
                                        "/leaderboard/history/{username}",
                                        "/quiz",
                                        "/quiz/grade/{grade}",
                                        "/api/contest/create",
                                        "/api/contest/join",
                                        "/api/contest/end/{roomCode}",
                                        "/api/contest/{roomCode}",
                                        "/api/contest/start/{roomCode}/{username}",
                                        "/gemini/process-image",
                                        "/streak/{username}",
                                        "/chatbot/{username}",
                                        "/leaderboard/{username}/{category}",
                                        "/parents/{id}",
                                        "/grade/{username}",
                                        "/getChild/{username}",
                                        "/api/contest/result/{roomCode}",
                                        "/api/contest/list/{username}",
                                        "/practice/bulk",
                                        "byGrade/{grade}",
                                        "/peers/{grade}")
                                .permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
