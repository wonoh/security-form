package com.wonoh.security.form.common;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityLogger {

    public static void log(String where){
        System.out.println(where);
        Thread thread = Thread.currentThread();
        System.out.println("Thread: "+thread.getName());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Principal:  "+principal);
    }
}
