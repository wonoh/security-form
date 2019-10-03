package com.wonoh.security.form.book;

import com.wonoh.security.form.account.Account;
import com.wonoh.security.form.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataGenerator implements ApplicationRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Account wonoh = createUser("wonoh");
        Account keesun = createUser("keesun");

        createBook("hibernate",wonoh);
        createBook("spring",keesun);

    }
    private void createBook(String title,Account account){

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(account);
        bookRepository.save(book);

    }
    private Account createUser(String name){

        Account account = new Account();
        account.setUsername(name);
        account.setPassword("123");
        account.setRole("USER");
        return accountService.createNew(account);
    }
}
