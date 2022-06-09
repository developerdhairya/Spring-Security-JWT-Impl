package tech.developerdhairya.securityclient.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebSecurity @Configuration @EnableWebMvc
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] WHITE_LIST_URLS={"/register","/"};


    public static final String[] PUBLIC_URLS={
            "/api/v1/auth/*",
            "v3/api-docs",
            "v2/api-dos",
            "/swagger-resources/*",
            "swagger-resources",
            "swagger-ui",
            "/webjars/**"

    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

//        http.userDetailsService(userService);


        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(PUBLIC_URLS).permitAll();

//        return http.build();

    }

}
