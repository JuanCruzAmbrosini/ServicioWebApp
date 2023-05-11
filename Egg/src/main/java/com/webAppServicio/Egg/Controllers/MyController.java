package com.webAppServicio.Egg.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author elias
 */
@Controller
public class MyController {

    @GetMapping("/")
    public String index(Model model) {
        return "index.html";
    }
}
