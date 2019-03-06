package com.sse.dynamicssedropbox.controllers;

import com.sse.dynamicssedropbox.models.User;
import com.sse.dynamicssedropbox.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public String register(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> params = request.getParameterMap();
        String username = params.get("username")[0];
        String password = params.get("password")[0];
        String confirmPassword = params.get("confirmPassword")[0];
        User user;
        if(password.equals(confirmPassword)) {
            user = service.register(username, password);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(token);
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
            return "app.html";
        }
        return null;
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> params = request.getParameterMap();
        String username = params.get("username")[0];
        String password = params.get("password")[0];
        User user = service.login(username, password);
        if(user == null) return null;
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(token);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
        return "app.html";
    }
}
