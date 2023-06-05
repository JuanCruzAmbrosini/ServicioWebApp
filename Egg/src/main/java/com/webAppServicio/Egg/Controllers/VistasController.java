package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Email.EnvioDeCorreo;
import com.webAppServicio.Egg.Entities.Person;
import com.webAppServicio.Egg.Entities.TechnicalService;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Services.ServiceOfServices;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class VistasController {

    @Autowired
    private ServiceOfServices serviciosTecnicos;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        List<TechnicalService> servicios = serviciosTecnicos.listarServicios();
        modelo.addAttribute("servicios", servicios);
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

    @GetMapping("/contact")
    public String contact(ModelMap modelo) {

        List<TechnicalService> servicios = serviciosTecnicos.listarServicios();
        modelo.addAttribute("servicios", servicios);

        return "contact.html";
    }

    @PostMapping("/contact_sent")
    public String contactSent(@RequestParam String nombreApellido, @RequestParam String email, @RequestParam String oficio, @RequestParam String comentario, ModelMap modelo) throws Exception {

        List<TechnicalService> servicios = serviciosTecnicos.listarServicios();
        modelo.addAttribute("servicios", servicios);

        try {

            String emailRemitente = "juancruz.ambrosini2@gmail.com";

            EnvioDeCorreo edc = new EnvioDeCorreo();
            edc.transfer_to_email(emailRemitente, comentario + " \n " + nombreApellido + "\n" + email, oficio);

            modelo.put("exito", "La consulta fue realizada con éxito!");

            return "contact.html";

        } catch (Exception ex) {

            modelo.put("error", "La consulta NO fue realizada con éxito, revise todos los campos e intente nuevamente.");

            return "contact.html";

        }

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
