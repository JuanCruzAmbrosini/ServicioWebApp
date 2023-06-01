package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Entities.Client;
import com.webAppServicio.Egg.Entities.Supplier;
import com.webAppServicio.Egg.Entities.TechnicalService;
import com.webAppServicio.Egg.Services.ClientService;
import com.webAppServicio.Egg.Services.ServiceOfServices;
import com.webAppServicio.Egg.Services.SupplierService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author elias
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClientService userS;

    @Autowired
    private SupplierService supplierS;

    @Autowired
    private ServiceOfServices serviciosTecnicos;

    @GetMapping("/dashboard")
    public String panel() {
        return "init_admin.html";
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

    @GetMapping("/service_list")
    public String listarServicios(ModelMap modelo) {
        List<TechnicalService> servicios = serviciosTecnicos.listarServicios();
        modelo.addAttribute("servicios", servicios);
        return "list_service.html";
    }
    
    @GetMapping("/profile")
    public String perfilAdmin() {
        return "profileAdmin.html";
    }
<<<<<<< HEAD
=======
    
>>>>>>> 35142c4bb6f2bd79b6407f0320e5a63dc262f8d4
}
