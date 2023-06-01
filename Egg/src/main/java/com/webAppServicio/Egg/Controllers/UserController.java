package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Entities.Client;
import com.webAppServicio.Egg.Entities.Person;
import com.webAppServicio.Egg.Entities.TechnicalService;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Services.ClientService;
import com.webAppServicio.Egg.Services.ServiceOfServices;
import com.webAppServicio.Egg.Services.SupplierService;
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

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ClientService userS;

    @Autowired
    private ServiceOfServices serviciosTecnicos;

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
        List<TechnicalService> servicios = serviciosTecnicos.listarServicios();
        modelo.addAttribute("servicios", servicios);
        return "init_user_serviceList.html";
    }

    @GetMapping("/service_description/{id}")
    public String verCaracteristicas(@PathVariable("id") String id, ModelMap modelo) {
        TechnicalService servicios = serviciosTecnicos.getOne(id);
        modelo.addAttribute("servicios", servicios);
        return "description_services.html";
    }

    @GetMapping("/profile")
    public String perfilUser(HttpSession session, String dni, ModelMap modelo) {
        Person usuario = (Person) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);
        return "profileUser.html";
    }

    @GetMapping("/delete/{dni}")
    public String eliminarUser(@PathVariable String dni, ModelMap modelo) {

        userS.eliminarUsuario(dni);

        return "redirect:/admin/user_list";
    }


    @GetMapping("/order_service")
    public String ordenServicioUsuario() {
        return "order_service_user.html";
    }
}
