package com.wonoh.security.form;

import com.wonoh.security.form.account.AccountContext;
import com.wonoh.security.form.account.AccountRepository;
import com.wonoh.security.form.common.SecurityLogger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.Callable;

@Controller
public class SampleController{

    private final SampleService sampleService;
    private final AccountRepository accountRepository;

    public SampleController(SampleService sampleService, AccountRepository accountRepository){
        this.sampleService = sampleService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/")
    public String index(Model model,Principal principal){
        if(principal == null){
            model.addAttribute("message","Hello Spring");
        }else{
            model.addAttribute("message","Hello " + principal.getName());
        }
        return "index";
    }
    @GetMapping("/info")
    public String info(Model model){
        model.addAttribute("message","Info");
        return "info";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("message","Hello " + principal.getName());
        AccountContext.setAccount(accountRepository.findByUsername(principal.getName()));
        sampleService.dashboard();
        return "dashboard";
    }
    @GetMapping("/admin")
    public String admin(Model model,Principal principal){
        model.addAttribute("message","Hello Admin " + principal.getName());
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model,Principal principal){
        model.addAttribute("message","Hello user " + principal.getName());
        return "user";
    }

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler(){

        SecurityLogger.log("MVC");

        // Callable 안에선 별도의 스레드로 실행
        return () -> {
            SecurityLogger.log("Callable");
            return "Async handler";
        };
    }

    @GetMapping("/async-service")
    @ResponseBody
    public String asyncService(){

        SecurityLogger.log("MVC, before async service");
        sampleService.asyncService();
        SecurityLogger.log("MVC, after async service");
        return "async service";
    }
}
