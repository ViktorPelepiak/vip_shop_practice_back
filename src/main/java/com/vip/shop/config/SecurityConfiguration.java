package com.vip.shop.config;

import com.vip.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.vip.shop")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] WHITE_LIST_URLS = {"/ping", "/admin", "/mail", "/security/registration", "/security/token/verification",
    "/security/token/update", "/security/login"};
    private static final String[] RESOURCE_URLS = {"/resources/**"};
    private static final String[] BLACK_LIST_URLS = {};
    private static final String[] ADMIN_URLS = {"machine/brand/**", "machine/model/**"};

    private final Environment environment;
    private final UserService userService;

    @Autowired
    public SecurityConfiguration(Environment environment, UserService userService) {
        this.environment = environment;
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Доступні HTTP методи
     */
    private final List<String> ALLOWED_METHODS = Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS");
    /**
     * Доступні HTTP заголовки
     */
    private final List<String> ALLOWED_HEADERS = Arrays.asList(
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials",
            "Access-Control-Allow-Headers",
            "Authorization",
            "Content-Type",
            "Accept",
            "withCredentials",
            "Set-Cookie");

    @Bean
    public CorsConfigurationSource corsConfigurationSource()  {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        ALLOWED_METHODS.forEach(configuration::addAllowedMethod);
        configuration.setAllowCredentials(true);
        ALLOWED_HEADERS.forEach(configuration::addAllowedHeader);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()

                .cors()
                .and()

                .csrf()
                .disable()

                // конфігурація авторизації
                .formLogin()
                .loginPage("/user/login/failed")
                .and()      // перевизначити відповідь в разі не-авторизації

                // конфігурація виходу з додатку
                .logout()
                .logoutSuccessUrl("/user/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                .and()

                .authorizeRequests()
                .antMatchers(
                        Stream.concat(Arrays.stream(WHITE_LIST_URLS), Arrays.stream(RESOURCE_URLS))
                                .toArray(String[]::new)
                )
                .permitAll()

//                .antMatchers(ADMIN_URLS)
//                .hasRole(UserRole.ADMIN.name())

                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
}
