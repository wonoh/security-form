package com.wonoh.security.form;

import com.wonoh.security.form.account.Account;
import com.wonoh.security.form.account.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountService accountService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    public void dashboard(){

        Account account = new Account();
        account.setRole("ADMIN");
        account.setUsername("wonoh");
        account.setPassword("w123");
        accountService.createNew(account);

        UserDetails userDetails = accountService.loadUserByUsername(account.getUsername());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,"w123");
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        sampleService.dashboard();
    }
}