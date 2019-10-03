package com.wonoh.security.form;

import com.wonoh.security.form.account.AccountContext;
import com.wonoh.security.form.account.AccountRepository;
import com.wonoh.security.form.account.UserAccount;
import com.wonoh.security.form.book.Book;
import com.wonoh.security.form.book.BookRepository;
import com.wonoh.security.form.common.SecurityLogger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.Callable;

@Controller
public class SampleController{

    private final SampleService sampleService;
    private final AccountRepository accountRepository;
    private final BookRepository bookRepository;

    public SampleController(SampleService sampleService, AccountRepository accountRepository,
                            BookRepository bookRepository){
        this.sampleService = sampleService;
        this.accountRepository = accountRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserAccount userAccount){
        if(userAccount == null){
            model.addAttribute("message","Hello Spring");
        }else{
            model.addAttribute("message","Hello " + userAccount.getUsername());
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
        model.addAttribute("books",bookRepository.findCurrentUserBooks());

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
