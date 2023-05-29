package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Entities.TechnicalService;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Services.ServiceOfServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/service_create")
public class ServiceController {

    @Autowired
    private ServiceOfServices serviciosTecnicos;

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
            return "redirect:/admin/dashboard";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("tipoServicio", tipoServicio);
            modelo.put("detalle", detalle);
            modelo.put("caracteristicas", caracteristicas);
            return "account_service.html";
        }
    }

}
