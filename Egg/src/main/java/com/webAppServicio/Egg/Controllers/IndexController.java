<<<<<<< HEAD
package com.webAppServicio.Egg.Controllers;

=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webAppServicio.Egg.Controllers;
>>>>>>> 4542ff191a2f0602a68103212e832f717b555a85
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

<<<<<<< HEAD
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public String index(ModelMap modelo) {
        return "index.html";
    }
=======
/**
 *
 * @author Juan Cruz
 */


@Controller
@RequestMapping("/")

public class IndexController {
    
    
    @GetMapping("/")
    
    public String index( ModelMap modelo ){
        
        return "index.html";    
        
    }
    
>>>>>>> 4542ff191a2f0602a68103212e832f717b555a85
}
