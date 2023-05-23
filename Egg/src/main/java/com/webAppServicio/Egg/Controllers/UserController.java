package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Entities.User;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userS;

    @GetMapping("/account_user")
    public String accountUser() {
        return "account_user.html";
    }

    @PostMapping("/registered_user")
    public String newUser(@RequestParam MultipartFile imagen, @RequestParam String dni, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam String telefono,
            @RequestParam String direccion, @RequestParam String barrio, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, @RequestParam String sexo, ModelMap modelo) throws MyException {
      
        try {
            userS.crearUsuario(imagen, dni, nombre, apellido, telefono, direccion, barrio, email, password, password2, sexo);
            modelo.put("exito", "Usuario registrado Correctamente");
            return "login.html";

        } catch (MyException ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("direccion", direccion);
            modelo.put("barrio", barrio);
            modelo.put("email", email);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.put("sexo", sexo);
            
            return "account_user.html";
        }

    }
    
//    @GetMapping("/lista")
//    public String lista(ModelMap modelo){
//        List <User> usuarios = userS.listarUsuarios();
//        modelo.addAttribute("usuarios", usuarios);
//        return "request_plumber.html";
//    }
    
}
