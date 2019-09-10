package com.wonoh.security.form.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE - 15)
//@EnableWebSecurity 는 자동설정 되기 때문에 추가할 필요없음.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // http 요청 인가 ( 어떻게 허용할것인지 )
        http.authorizeRequests()
                .mvcMatchers("/","/info","/account/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.formLogin();
        http.httpBasic();
    }

}
