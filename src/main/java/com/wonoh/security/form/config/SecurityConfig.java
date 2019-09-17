package com.wonoh.security.form.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Collections;
import java.util.List;

@Configuration
//@EnableWebSecurity 는 자동설정 되기 때문에 추가할 필요없음.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //accessDecisionManager 커스터마이징
    public AccessDecisionManager accessDecisionManager(){

        // role admin 은 role user 의 상위 롤이라고 hierarchy 설정
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(handler);

        List<AccessDecisionVoter<? extends Object>> voters = Collections.singletonList(webExpressionVoter);

        return new AffirmativeBased(voters);
    }

    // expressionHandler 커스터마이징
    public SecurityExpressionHandler expressionHandler(){

        // role admin 은 role user 의 상위 롤이라고 hierarchy 설정
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        return handler;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // http 요청 인가 ( 어떻게 허용할것인지 )
        http.authorizeRequests()
                .mvcMatchers("/","/info","/account/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .mvcMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
                .accessDecisionManager(accessDecisionManager())
                // expressionHandler 로도 roleHierarchy 설정 가능!
                .expressionHandler(expressionHandler());

        http.formLogin();
        http.httpBasic();
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
