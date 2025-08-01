package com.smartfixsamana.auth;

import java.util.Arrays;
import java.util.List;

import com.smartfixsamana.auth.filter.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationConfiguration authenticationConfiguration) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(
                                        //Clientes
                    athz -> athz
                                .requestMatchers(HttpMethod.GET, "/customers", "/customers/page/{page}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/customers/{id}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/customers").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/customers/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/customers/{id}").hasRole("ADMIN")

                                        //Celulares
                                .requestMatchers(HttpMethod.GET, "/phones", "/phones/page/{page}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/phones/{id}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/phones").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/phones/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/phones/{id}").hasRole("ADMIN")

                                        //Reparaciones
                                .requestMatchers(HttpMethod.GET, "/repairs", "repairs/page/{page}").permitAll()  
                                .requestMatchers(HttpMethod.GET, "/repairs/{id}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/repairs").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/repairs/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/repairs/{id}").hasRole("ADMIN")

                                        //Catalogo de Repuestos
                                .requestMatchers(HttpMethod.GET, "/part_catalog").permitAll()      
                                .requestMatchers(HttpMethod.GET, "/part_catalog/{id}").hasAnyRole("USER", "ADMIN")
                                
                                        //Repuestos
                                .requestMatchers(HttpMethod.GET, "/parts", "/parts/page/{page}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/parts/{id}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/parts").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/parts/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/parts/{id}").hasRole("ADMIN")

                                    //Usuarios
                            .requestMatchers(HttpMethod.POST, "/userslogin").permitAll()
                            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                            .anyRequest().authenticated())
                .cors(cors -> cors.configurationSource(configurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    CorsConfigurationSource configurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;

    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {

        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
                new CorsFilter(this.configurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;

    }

}
