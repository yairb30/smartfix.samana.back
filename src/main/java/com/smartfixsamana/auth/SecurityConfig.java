package com.smartfixsamana.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.smartfixsamana.auth.filter.JwtAuthenticationFilter;
import com.smartfixsamana.auth.filter.JwtValidationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(
                                        //Clientes
                    athz -> athz.requestMatchers(HttpMethod.GET, "/clientes", "/clientes/page/{page}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/clientes/{id}").hasAnyRole("CLIENTE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/clientes/nombre").hasAnyRole("CLIENTE", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/clientes").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/clientes/{id}").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/clientes/{id}").hasAnyRole("ADMIN")

                                        //Celulares
                                .requestMatchers(HttpMethod.GET, "/celulares", "/celulares/page/{page}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/celulares/{id}").hasAnyRole("CLIENTE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/celulares/marca").hasAnyRole("CLIENTE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/celulares/modelo").hasAnyRole("CLIENTE", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/celulares").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/celulares/{id}").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/celulares/{id}").hasAnyRole("ADMIN")

                                        //Reparaciones
                                .requestMatchers(HttpMethod.GET, "/reparaciones", "reparaciones/page/{page}").permitAll()  
                                .requestMatchers(HttpMethod.GET, "/reparaciones/{id}").hasAnyRole("CLIENTE", "ADMIN")     
                                .requestMatchers(HttpMethod.GET, "/reparaciones/search/cliente").hasAnyRole("CLIENTE", "ADMIN")   
                                .requestMatchers(HttpMethod.GET, "/reparaciones/search/celular").hasAnyRole("CLIENTE", "ADMIN") 
                                .requestMatchers(HttpMethod.POST, "/reparaciones").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/reparaciones/{id}").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/reparaciones/{id}").hasAnyRole("ADMIN")

                                        //ListaRepuestos
                                .requestMatchers(HttpMethod.GET, "/lis_repuestos").permitAll()      
                                .requestMatchers(HttpMethod.GET, "/lis_repuestos/{id}").hasAnyRole("CLIENTE", "ADMIN")     
                                .requestMatchers(HttpMethod.POST, "/lis_repuestos").hasAnyRole("ADMIN")     
                                .requestMatchers(HttpMethod.PUT, "/lis_repuestos/{id}").hasAnyRole("ADMIN")     
                                .requestMatchers(HttpMethod.DELETE, "/lis_repuestos/{id}").hasAnyRole("ADMIN")
                                
                                        //Repuestos
                                .requestMatchers(HttpMethod.GET, "/repuestos", "/repuestos/page/{page}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/repuestos/{id}").hasAnyRole("CLIENTE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/repuestos/search/repuesto").hasAnyRole("CLIENTE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/repuestos/search/celular").hasAnyRole("CLIENTE", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/repuestos").hasAnyRole("ADMIN")     
                                .requestMatchers(HttpMethod.PUT, "/repuesto/{id}").hasAnyRole("ADMIN")     
                                .requestMatchers(HttpMethod.DELETE, "/repuesto/{id}").hasAnyRole("ADMIN")

                                    //Usuarios
                                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                                        
                                .anyRequest().authenticated())
                .cors(cors -> cors.configurationSource(configurationSource()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config -> config.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }

    @Bean
    CorsConfigurationSource configurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;

    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {

        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<CorsFilter>(
                new CorsFilter(this.configurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;

    }

}
