package edu.miu590.bookingservice.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class ApplicationUtil {
    public static String getCurrentUser(){
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
