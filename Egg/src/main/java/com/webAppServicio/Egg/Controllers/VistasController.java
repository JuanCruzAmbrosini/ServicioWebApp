package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Entities.Supplier;
import com.webAppServicio.Egg.Entities.Client;
import com.webAppServicio.Egg.Entities.TechnicalService;
import com.webAppServicio.Egg.Services.ServiceOfServices;
import com.webAppServicio.Egg.Services.SupplierService;
import com.webAppServicio.Egg.Services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")

public class VistasController {

    @Autowired
    private UserService userS;

    @Autowired
    private SupplierService supplierS;
    
    @Autowired
    private ServiceOfServices serviciosTecnicos;
    
    @GetMapping("/")
    public String index(ModelMap modelo) {
        return "index.html";
    }

    @GetMapping("/about")
    public String about() {
        return "about.html";
    }

    @GetMapping("/serviceList")
    public String servicios() {
        return "service.html";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact.html";
    }

    @GetMapping("/account")
    public String account() {
        return "new_account.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/description_plumber")
    public String descriptionPlumber() {
        return "description_plumber.html";
    }

    @GetMapping("/description_electric")
    public String descriptionElectric() {
        return "description_electric.html";
    }

    @GetMapping("/description_gas")
    public String descriptionGas() {
        return "description_gas.html";
    }

    @GetMapping("/description_gardener")
    public String descriptionGarden() {
        return "description_gardener.html";
    }

    @PostMapping("/registered_supplier")
    public String newSupplier() {
        return "login.html";
    }

    @GetMapping("/admin")
    public String indexAdmin(){
        return "indexAdmin.html";
    }
    
    @GetMapping("/user_list")
    public String listarUsuarios(ModelMap modelo) {
        List<Client> usuarios = userS.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "list_user.html";
    }

    @GetMapping("/supplier_list")
    public String listarProveedores(ModelMap modelo) {
        List<Supplier> proveedores = supplierS.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);

        return "list_supplier.html";
    }
}
