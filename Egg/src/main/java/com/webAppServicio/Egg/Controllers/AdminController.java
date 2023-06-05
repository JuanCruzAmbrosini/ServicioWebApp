package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Entities.Client;
import com.webAppServicio.Egg.Entities.Person;
import com.webAppServicio.Egg.Entities.Supplier;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String perfilAdmin(HttpSession session, ModelMap modelo) {
        Person usuario = (Person) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);
        return "profileAdmin.html";
    }

    @GetMapping("/profile_edit")
    public String perfilUserEdit(HttpSession session, ModelMap modelo) {
        Person usuario = (Person) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);
        return "modification_admin.html";
    }

    @PostMapping("/modification_profile/{dni}")
    public String perfilModificado(@PathVariable String dni, @RequestParam MultipartFile imagen, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam("correoOculto") String email,
            @RequestParam String telefono, @RequestParam String direccion, @RequestParam String sexo, @RequestParam String password, @RequestParam String password2, @RequestParam String barrio,
            ModelMap modelo, HttpSession session, RedirectAttributes redirectAttributes) throws MyException {
         try {
            userS.modificarPerfilAdmin(imagen, dni, nombre, apellido, barrio, telefono, direccion, email, password, password2, sexo);
            redirectAttributes.addFlashAttribute("exito", "Tu Perfil Se Actualizo Correctamente, Inicie Sesion Nuevamente Para Ver Los Cambios"); 
            return "redirect:/admin/profile";

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

            return "redirect:/admin/modification_profile/{dni}";

        }
    }

    @GetMapping("/account_service")
    public String registroServicio() {
        return "account_service.html";
    }

    @PostMapping("/newService")
    public String newService(@RequestParam String tipoServicio, @RequestParam String detalle,
            @RequestParam String caracteristicas,
            @RequestParam MultipartFile imagen, ModelMap modelo, RedirectAttributes redirectAttributes) throws MyException {

        try {
            serviciosTecnicos.crearServicio(tipoServicio, detalle, caracteristicas, imagen);
            redirectAttributes.addFlashAttribute("exito", "Servicio Registrado Correctamente");
            return "redirect:/admin/service_list";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("tipoServicio", tipoServicio);
            modelo.put("detalle", detalle);
            modelo.put("caracteristicas", caracteristicas);
            return "account_service.html";
        }
    }

    @GetMapping("/supplier_delete/{dni}")
    public String eliminarProveedor(@PathVariable String dni, RedirectAttributes redirectAttributes) {
        supplierS.eliminarProveedor(dni);
        redirectAttributes.addFlashAttribute("exito", "Tecnico Eliminado Correctamente");
        return "redirect:/admin/supplier_list";
    }

    @GetMapping("/user_delete/{dni}")
    public String eliminarUser(@PathVariable String dni, RedirectAttributes redirectAttributes) {
        userS.eliminarUsuario(dni);
        redirectAttributes.addFlashAttribute("exito", "Usuario Eliminado Correctamente");
        return "redirect:/admin/user_list";
    }

    @GetMapping("/service_delete/{id}")
    public String eliminarServicio(@PathVariable String id, RedirectAttributes redirectAttributes) {
        List<Supplier> proveedores = supplierS.listarProveedoresPorOficioUsandoId(id);
        for (Supplier proveedor : proveedores) {
            supplierS.eliminarProveedor(proveedor.getDni());
        }
        serviciosTecnicos.eliminarServicio(id);
        redirectAttributes.addFlashAttribute("exito", "Servicio Eliminado Correctamente");
        return "redirect:/admin/service_list";
    }

}
