package com.changan.carbond.mvc;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    @RequestMapping("login")
    public String login() {
        return "login";
    }
}
