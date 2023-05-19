package com.webAppServicio.Egg.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orderService")
public class OrderServiceController {
    
    @GetMapping("/detail_sevice")
    public String detalleServicio(){
        return "detail_service.html";
    }
}
