package com.webAppServicio.Egg.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")

public class VistasController {

    @GetMapping("/")
    public String index(ModelMap modelo) {
        return "index.html";
    }

    @GetMapping("/about")
    public String about (){
        return "about.html";
    }
    
    @GetMapping("/service")
    public String service(){
        return "service.html";
    }
    
    @GetMapping("/contact")
    public String contact(){
        return "contact.html";
    }
    
    @GetMapping("/account")
    public String account(){
        return "new_account.html";
    }
    
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
    
    @GetMapping("/account_user")
    public String accountUser(){
        return "account_user.html";
    }
    
    @GetMapping("/account_supplier")
    public String accountSupplier(){
        return "account_supplier.html";
    }
}
