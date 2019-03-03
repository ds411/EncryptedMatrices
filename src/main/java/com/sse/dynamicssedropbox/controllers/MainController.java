package com.sse.dynamicssedropbox.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
public class MainController {

    //Home page
    @GetMapping("/")
    public String home() {
        return "home.html";
    }

}
