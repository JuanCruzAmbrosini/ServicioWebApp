package com.webAppServicio.Egg.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
    
    @GetMapping("/account_supplier")
    public String accountSupplier(){
        return "account_supplier.html";
    }
    
    @PostMapping("/registered_supplier")
    public String newSupplier(){
        return "login.html";
    }
}
