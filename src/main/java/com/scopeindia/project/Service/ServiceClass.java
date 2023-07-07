package com.scopeindia.project.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
@Service
public class ServiceClass {
    public  String generateRandomString() {
        String randomString = UUID.randomUUID().toString();
        // Remove hyphens and return the random string
        String password=randomString.substring(0,6);
        return password.replace("-", "");
    
}
    

    public int generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        return randomNumber;
    }
    public boolean isLoggedIn(HttpServletRequest request) {
        // Check if the user is logged in by checking the cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("loggedInUser".equals(cookie.getName())) {
                    String loggedInUser = cookie.getValue();
                    return StringUtils.hasText(loggedInUser);
                }
            }
        }
        return false;
    }
    public boolean isCookiesEmpty(Cookie[] cookies) {
        if (cookies == null || cookies.length == 0) {
            return true;
        }
        
//        for (Cookie cookie : cookies) {
//            if (!StringUtils.hasText(cookie.getValue())) {
//                return false; // At least one non-empty cookie found
//            }
//        }
        
        return false; // All cookies are empty
    }
    public List<String> getCookieValues(Cookie[] cookies) {
        List<String> cookieValues = new ArrayList<>();
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieValues.add(cookie.getValue());
            }
        }
        
        return cookieValues;
    }
    public void removeCookies(Cookie[] cookies, HttpServletResponse response) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0); // Set expiration to a past date
                cookie.setValue(""); // Clear the value
                cookie.setPath("/"); // Set the cookie path
                response.addCookie(cookie); // Add the updated cookie to the response
            }
        }
    }
    public void emailsending( SimpleMailMessage mailMessage) {
    	
    }
}
