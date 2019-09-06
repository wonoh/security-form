package com.wonoh.security.form.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // http 요청 인가 ( 어떻게 허용할것인지 )
        http.authorizeRequests()
                .mvcMatchers("/","/info").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.formLogin();
        http.httpBasic();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //{noop} 기본적인 패스워드 인코더
        auth.inMemoryAuthentication()
                .withUser("wonoh").password("{noop}123").roles("USER")
                .and()
                .withUser("admin").password("{noop}!@#").roles("ADMIN");
    }
}
