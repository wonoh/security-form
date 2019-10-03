package com.wonoh.security.form.account;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private final AccountService accountService;

    public SignUpController(AccountService accountService){
        this.accountService = accountService;
    }


    @GetMapping
    public String signUpForm(Model model){
        model.addAttribute("account",new Account());
        return "signup";
    }

    @PostMapping
    public String processSignUp(@ModelAttribute Account account){

        account.setRole("USER");
        accountService.createNew(account);
        return "redirect:/";

    }
}
