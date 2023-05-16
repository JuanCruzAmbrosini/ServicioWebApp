package com.webAppServicio.Egg.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class usuarioController {
    
    @GetMapping("/account_user")
    public String accountUser(){
        return "account_user.html";
    }
}
