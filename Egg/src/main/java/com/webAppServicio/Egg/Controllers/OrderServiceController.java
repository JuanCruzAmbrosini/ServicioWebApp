package com.webAppServicio.Egg.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderServiceController {
    
    @GetMapping("/detail_service")
    public String detalleOrden(){
        return "detail_service";
    }
}
