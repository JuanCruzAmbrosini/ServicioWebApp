package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Entities.Person;
import com.webAppServicio.Egg.Entities.TechnicalService;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Services.ServiceOfServices;
import com.webAppServicio.Egg.Services.SupplierService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierS;

    @Autowired
    private ServiceOfServices serviceS;
    
    @GetMapping("/account_supplier")
    public String accountSupplier(ModelMap modelo) {
        List<TechnicalService> servicios = serviceS.listarServicios();
        modelo.addAttribute("servicios", servicios);
        return "account_supplier.html";
    }

    @PostMapping("/registered_supplier")
    public String newSupplier(@RequestParam String dni, @RequestParam MultipartFile imagen, @RequestParam String matricula, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam String email,
            @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, @RequestParam String tipoServicio,
            ModelMap modelo) throws MyException {

        try {

            TechnicalService oficio = serviceS.buscarServicioPorTipo(tipoServicio);
            supplierS.crearProveedor(imagen, dni, matricula, nombre, apellido, telefono, email, password, password2, tipoServicio );
            modelo.put("exito", "Proveedor registrado Correctamente");
            return "login.html";

        } catch (MyException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("dni", dni);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("tipoServicio", tipoServicio);
            modelo.put("email", email);
            modelo.put("telefono", telefono);
            modelo.put("password", password);
            modelo.put("password2", password2);

            return "account_supplier.html";

        }

    }

    @GetMapping("/init")
    public String inicio() {
        return "init_supplier.html";
    }

    @GetMapping("/order_service")
    public String ordenServicioProveedor(ModelMap modelo){
        return "order_service_supplier.html";
    }
    
    @GetMapping("/profile")
    public String perfilSupplier(HttpSession session, ModelMap modelo) {
        Person proveedor = (Person) session.getAttribute("usuariosession");
        modelo.addAttribute("proveedor", proveedor);
        return "profileSupplier.html";
    }
    
    @GetMapping("/profile_edit")
    public String perfilSupplierEdit(HttpSession session, ModelMap modelo) {
        Person proveedor = (Person) session.getAttribute("usuariosession");
        modelo.addAttribute("proveedor", proveedor);
        return "modification_supplier.html";
    }
    
    @PostMapping("/modification_profile/{dni}")
    public String perfilModificado(@RequestParam("dniOculto") String dni, @RequestParam MultipartFile imagen, @RequestParam String matricula, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam("correoOculto") String email,
            @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, @RequestParam("oficioOculto") String tipoServicio,
            ModelMap modelo, RedirectAttributes redirectAttributes) throws MyException {
         try {
            TechnicalService oficio = serviceS.buscarServicioPorTipo(tipoServicio);
            supplierS.modificarPerfil(imagen, dni, matricula, nombre, apellido, telefono, email, password, password2, tipoServicio);
            modelo.put("exito", "Proveedor Modificado Correctamente");
            
            return "redirect:/supplier/init";

        } catch (MyException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("oficio", tipoServicio);
            modelo.put("telefono", telefono);
            modelo.put("password", password);
            modelo.put("password2", password2);

            return "modifcation_supplier.html";

        }
    }
}
