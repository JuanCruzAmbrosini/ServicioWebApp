package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Entities.Client;
import com.webAppServicio.Egg.Entities.Person;
import com.webAppServicio.Egg.Entities.Supplier;
import com.webAppServicio.Egg.Entities.TechnicalService;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Services.ClientService;
import com.webAppServicio.Egg.Services.ServiceOfServices;
import com.webAppServicio.Egg.Services.SupplierService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ClientService userS;

    @Autowired
    private ServiceOfServices servicioS;
    
    @Autowired
    private SupplierService supplierS;

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

    @GetMapping("/init")
    public String initCliente() {

        return "init_user.html";

    }

    @GetMapping("/serviceList")
    public String listarServicios(ModelMap modelo) {
        List<TechnicalService> servicios = servicioS.listarServicios();
        modelo.addAttribute("servicios", servicios);
        return "init_user_serviceList.html";
    }

    @GetMapping("/service_description/{id}")
    public String verCaracteristicas(@PathVariable("id") String id, ModelMap modelo) {
        TechnicalService servicios = servicioS.getOne(id);
        modelo.addAttribute("servicios", servicios);
        return "description_services.html";
    }

    @GetMapping("/tecnicos/{tipoServicio}")
    public String mostrarTecnicos(@PathVariable String tipoServicio, ModelMap tecnicos) {

        List<Supplier> tecnicosPorOficio = new ArrayList<>();
        tecnicosPorOficio = supplierS.listarProveedoresPorOficio(tipoServicio);
        tecnicos.addAttribute("tecnicosPorOficio",tecnicosPorOficio);
        
        return "request_supplier.html";
    }
    
    @GetMapping("/order_service")
    public String ordenServicioUsuario(){
        return "order_service_user.html";
    }
    
    @GetMapping("/profile")
    public String perfilUser(HttpSession session, ModelMap modelo) {
        Person usuario = (Person) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);
        return "profileUser.html";
    }
    
        @GetMapping("/profile_edit")
    public String perfilUserEdit(HttpSession session, ModelMap modelo) {
        Person usuario = (Person) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);
        return "modification_user.html";
    }
    
    @PostMapping("/modification_profile/{dni}")
    public String perfilModificado(@PathVariable String dni, @RequestParam MultipartFile imagen, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam("correoOculto") String email,
            @RequestParam String telefono, @RequestParam String direccion, @RequestParam String sexo, @RequestParam String password, @RequestParam String password2, @RequestParam String barrio,
            ModelMap modelo, HttpSession session, RedirectAttributes redirectAttributes) throws MyException {
         try {
            userS.modificarPerfil(imagen, dni, nombre, apellido, barrio, telefono, direccion, email, password, password2, sexo);
            redirectAttributes.addFlashAttribute("exito", "Tu Perfil Se Actualizo Correctamente, Inicie Sesion Nuevamente Para Ver Los Cambios"); 
            return "redirect:/user/profile";

        } catch (MyException ex) {
            redirectAttributes.addFlashAttribute("error", "Tu Perfil No Se Actualizo");
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
            modelo.put("password", password);
            modelo.put("password2", password2);
            modelo.put("sexo", sexo);
            modelo.put("direccion", direccion);

            return "redirect:/user/modification_profile/{dni}";

        }
    }
}
