package com.wonoh.security.form.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController{

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @GetMapping(value = "/account/{role}/{username}/{password}")
    public Account createAccount(@ModelAttribute Account account){

        return accountRepository.save(account);

    }
}
