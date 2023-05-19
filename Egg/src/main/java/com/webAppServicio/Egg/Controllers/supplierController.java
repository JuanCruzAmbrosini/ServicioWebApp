package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/supplier")
public class supplierController {
    
     @Autowired
    private SupplierService supplierS;
    
    @GetMapping("/account_supplier")
    public String accountSupplier(){
        return "account_supplier.html";
    }
    
    @PostMapping("/registered_supplier")
    public String newSupplier(@RequestParam MultipartFile imagen, @RequestParam String matricula, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam String email,
            @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, @RequestParam String oficio,
            ModelMap modelo) throws MyException{
        
        try {
            
            supplierS.crearProveedor(imagen, matricula, nombre, apellido, telefono, email, password, password2, oficio);
            modelo.put("exito", "Proveedor registrado Correctamente");
            return "login.html";
            
        } catch (MyException ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("oficio", oficio);
            modelo.put("email", email);
            modelo.put("telefono", telefono);
            modelo.put("password", password);
            modelo.put("password2", password2);
            
            return "account_supplier.html";
            
        }
       
    }
}
