package com.ucb.Rapidex.config;

import com.ucb.Rapidex.service.SuporteUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SuporteSecurityConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain suporteFilterChain(HttpSecurity http,
                                                   SuporteUserDetailsService suporteUserDetailsService,
                                                   PasswordEncoder passwordEncoder) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(suporteUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return http
                .securityMatcher("/suporte/**")
                .authenticationProvider(provider)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/suporte/login")
                        .loginProcessingUrl("/suporte/login")
                        .defaultSuccessUrl("/suporte/painel", true)
                        .failureUrl("/suporte/login?erro")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/suporte/logout")
                        .logoutSuccessUrl("/suporte/login?logout")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            String accept = request.getHeader("Accept");
                            if (accept != null && accept.contains("text/html")) {
                                response.sendRedirect("/suporte/login");
                            } else {
                                response.setContentType("application/json");
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.getWriter().write("{\"erro\":\"Nao autenticado\"}");
                            }
                        })
                )
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/suporte/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                .build();
    }
}