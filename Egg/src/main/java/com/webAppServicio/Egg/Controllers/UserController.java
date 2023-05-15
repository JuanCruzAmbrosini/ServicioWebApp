
package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Exceptions.UserException;
import com.webAppServicio.Egg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/usuario")

public class UserController {
 
    @Autowired
    private UserService userS;
    
    @GetMapping("/registrar")
    public String registrar(){
        
        return "new_account.html";
        
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String dni, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam String direccion, @RequestParam String email, @RequestParam String password, @RequestParam String sexo) throws UserException{
        
        userS.crearUsuario(dni, nombre, apellido, telefono, direccion, email, password, sexo);
        
         return "redirect:../";
        
    }
    
}
