package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Entities.Person;
import com.webAppServicio.Egg.Entities.TechnicalService;
import com.webAppServicio.Egg.Services.ServiceOfServices;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class VistasController {

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
    public String listarServicios(ModelMap modelo) {
        List<TechnicalService> servicios = serviciosTecnicos.listarServicios();
        modelo.addAttribute("servicios", servicios);
        return "service.html";
    }

//    @GetMapping("/service_description/{id}")
//    public String verCaracteristicas(@PathVariable("id") String id, ModelMap modelo) {
//        TechnicalService servicios = serviciosTecnicos.getOne(id);
//        modelo.addAttribute("servicios", servicios);
//        return "description_services.html";
//    }

    @GetMapping("/contact")
    public String contact() {
        return "contact.html";
    }

    @GetMapping("/account")
    public String account() {
        return "new_account.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {

            modelo.put("error", "¡Usuario o contraseña invalidos!");

        }

        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUPPLIER')")
    @GetMapping("/init")
    public String inicio(HttpSession session) {
        Person logueado = (Person) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";

        } else if (logueado.getRol().toString().equals("SUPPLIER")) {
            return "redirect:/supplier/init";

        } else {

            return "redirect:/user/init";

        }
    }

}
