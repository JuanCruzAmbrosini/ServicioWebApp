package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Entities.Client;
import com.webAppServicio.Egg.Entities.Supplier;
import com.webAppServicio.Egg.Entities.TechnicalService;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Services.ClientService;
import com.webAppServicio.Egg.Services.ServiceOfServices;
import com.webAppServicio.Egg.Services.SupplierService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    
        @GetMapping("/account_service")
    public String registroServicio() {
        return "account_service.html";
    }
    
        @PostMapping("/newService")
    public String newService(@RequestParam String tipoServicio, @RequestParam String detalle,
            @RequestParam String caracteristicas,
            @RequestParam MultipartFile imagen, ModelMap modelo) throws MyException {

        try {
            serviciosTecnicos.crearServicio(tipoServicio, detalle, caracteristicas, imagen);
            modelo.put("exito", "Servicio Registrado Correctamente");
        return "list_service.html";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("tipoServicio", tipoServicio);
            modelo.put("detalle", detalle);
            modelo.put("caracteristicas", caracteristicas);
            return "account_service.html";
        }
    }
    
        @GetMapping("/service_delete/{id}")
    public String eliminarProveedor(@PathVariable String id) {
        
         List<Supplier> proveedores = supplierS.listarProveedoresPorOficioUsandoId(id);
        
        for (Supplier proveedor : proveedores) {
            
            supplierS.eliminarProveedor(proveedor.getDni());
            
        }
        
        serviciosTecnicos.eliminarServicio(id);
            
        return "list_service.html";
    }
    
}
