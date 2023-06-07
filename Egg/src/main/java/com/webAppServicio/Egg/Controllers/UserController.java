package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Email.EnvioDeCorreo;
import com.webAppServicio.Egg.Entities.*;
import com.webAppServicio.Egg.Enums.EstatusOrden;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Services.ClientService;
import com.webAppServicio.Egg.Services.OrderServiceServices;
import com.webAppServicio.Egg.Services.ServiceOfServices;
import com.webAppServicio.Egg.Services.SupplierService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
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

    @Autowired
    private OrderServiceServices orderS;

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
    public String mostrarTecnicos(@PathVariable String tipoServicio, ModelMap modelo) {

        List<Supplier> tecnicosPorOficio = new ArrayList<>();
        tecnicosPorOficio = supplierS.listarProveedoresPorOficio(tipoServicio);
        modelo.addAttribute("tecnicosPorOficio", tecnicosPorOficio);
        modelo.addAttribute("tipoServicio", tipoServicio);

        return "request_supplier.html";
    }

    @GetMapping("/order_service")
    public String ListaOrdenPorUsuario(ModelMap modelo, HttpSession session) {

        Client usuario = (Client) session.getAttribute("usuariosession");
        List<OrderService> ordenes = orderS.listarOrdenesPorClienteId(usuario.getDni());

        modelo.addAttribute("ordenes", ordenes);

        return "order_service_user.html";
    }

    @GetMapping("/order_service_budgeted")
    public String ListaOrdenPorUsuarioPresupuestadas(ModelMap modelo, HttpSession session) {

        Client usuario = (Client) session.getAttribute("usuariosession");
        List<OrderService> ordenes = orderS.listarOrdenesPorClienteId(usuario.getDni());

        modelo.addAttribute("ordenes", ordenes);

        return "order_service_user_budgeted.html";
    }

    @PostMapping("/order_service_budgeted_aproved/{id}")
    public String aprovarOrden(@PathVariable Integer id, ModelMap modelo, HttpSession session, RedirectAttributes redirectAttributes) {

        OrderService orden = orderS.getOne(id);
        EnvioDeCorreo edc = new EnvioDeCorreo();
        Client usuario = (Client) session.getAttribute("usuariosession");

        try {

            orderS.modificarOrden(orden.getId(), orden.getOficio().getTipoServicio(),
                    orden.getDetalleOrden(), orden.getPresupuesto(),
                    String.valueOf(EstatusOrden.DESARROLLO), orden.getFechaRecibida());

            edc.transfer_to_email(orden.getProveedor().getEmail(), "Buenas noticias! " + usuario.getNombre() + " "
                    + usuario.getApellido() + " ha aceptado la orden (orden N° " + orden.getId() + ")! \nEl usuario te estará esperando a la hora que has marcado.\nMuchas gracias por usar nuestros servicios!",
                    "Orden N°" + orden.getId() + " aceptada! Felicidades!");
            redirectAttributes.addFlashAttribute("exito", "La orden ha sido aceptada y se le informará al tecnico a la brevedad! \nGracias por usar nuestros servicios!");

            return "redirect:/user/order_service_budgeted";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error. Inténtelo nuevamente mas tarde! \nGracias por usar nuestros servicios!");
            return "redirect:/user/order_service_budgeted";
        }

    }

    @GetMapping("/order_service_in_process")
    public String ListaOrdenPorUsuarioEnProceso(ModelMap modelo, HttpSession session) {

        Client usuario = (Client) session.getAttribute("usuariosession");
        List<OrderService> ordenes = orderS.listarOrdenesPorClienteId(usuario.getDni());

        modelo.addAttribute("ordenes", ordenes);
        return "order_service_user_in_process.html";
    }

    @GetMapping("/order_service_finally")
    public String ListaOrdenPorUsuarioFinalizadas(ModelMap modelo, HttpSession session) {

        Client usuario = (Client) session.getAttribute("usuariosession");
        List<OrderService> ordenes = orderS.listarOrdenesPorClienteId(usuario.getDni());

        modelo.addAttribute("ordenes", ordenes);
        return "order_service_user_finally.html";
    }

    @GetMapping("/order_scoring/{id}")
    public String CalificacionPorUsuarioAProveedor(@PathVariable Integer id, ModelMap modelo, HttpSession session) {

    Supplier tecnico = orderS.getOne(id).getProveedor();

    modelo.addAttribute("tecnico", tecnico);

        return "order_service_puntuaction.html";
    }

    @GetMapping("/profile")
    public String perfilUser(HttpSession session, ModelMap modelo) {
        Person usuario = (Person) session.getAttribute("usuariosession");
        modelo.addAttribute("usuario", usuario);
        return "profileUser.html";
    }

    @GetMapping("/profile_edit/{dni}")
    public String perfilUserEdit(@PathVariable String dni, ModelMap modelo) {
        Person usuario = userS.getOne(dni);
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
            
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            redirectAttributes.addFlashAttribute("nombre", nombre);
            redirectAttributes.addFlashAttribute("apellido", apellido);
            redirectAttributes.addFlashAttribute("telefono", telefono);
            redirectAttributes.addFlashAttribute("password", password);
            redirectAttributes.addFlashAttribute("password2", password2);
            redirectAttributes.addFlashAttribute("sexo", sexo);
            redirectAttributes.addFlashAttribute("direccion", direccion);

            return "redirect:/user/profile_edit/{dni}";

        }
    }

    @GetMapping("/new_order/{dni}")
    public String nuevaOrden(ModelMap modelo, @PathVariable String dni) {

        modelo.addAttribute("tecnico", supplierS.getOne(dni));

        return "order_creation.html";
    }

    @GetMapping("/order_cancel/{id}")
    public String cancelarOrdenSinCotizar(@PathVariable Integer id, RedirectAttributes redirectAttributes) throws MyException {
        OrderService order = orderS.getOne(id);

        EnvioDeCorreo edc = new EnvioDeCorreo();
        edc.transfer_to_email(order.getProveedor().getEmail(), "El usuario: " + order.getUsuario().getNombre() + " "
                + order.getUsuario().getApellido() + " ha cancelado la orden pendiente (id N°= " + order.getId() + ") \n Gracias por utilizar nuestros servicios.", "Orden N°"
                + order.getId() + " cancelada.");

        orderS.eliminarOrden(id);

        redirectAttributes.addFlashAttribute("exito", "La orden ha sido cancelada con éxito.");

        return "redirect:/user/order_service";
    }

    @GetMapping("/order_cancel_budgeted/{id}")
    public String cancelarOrdenCotizada(@PathVariable Integer id, RedirectAttributes redirectAttributes) throws MyException {
        OrderService order = orderS.getOne(id);

        EnvioDeCorreo edc = new EnvioDeCorreo();
        edc.transfer_to_email(order.getProveedor().getEmail(), "El usuario: " + order.getUsuario().getNombre() + " "
                + order.getUsuario().getApellido() + " ha cancelado la orden pendiente (id N°= " + order.getId() + ") \n Gracias por utilizar nuestros servicios.", "Orden N°"
                + order.getId() + " cancelada.");

        orderS.eliminarOrden(id);

        redirectAttributes.addFlashAttribute("exito", "La orden ha sido cancelada con éxito.");

        return "redirect:/user/order_service_budgeted";
    }

    @PostMapping("/new_order_done/{dni}")
    public String nuevaOrdenCreada(@PathVariable String dni, @RequestParam String detalleOrden,
            ModelMap modelo, HttpSession session, RedirectAttributes redirectAttributes) {

        Client usuario = (Client) session.getAttribute("usuariosession");
        Supplier supplier = supplierS.getOne(dni);
        String tipoServicio = supplier.getOficio().getTipoServicio();
        Date fechaActual = new Date();

        try {

            EnvioDeCorreo edc = new EnvioDeCorreo();

            orderS.createOrder(tipoServicio, detalleOrden, usuario, supplier);

            edc.transfer_to_email(supplier.getEmail(), detalleOrden + "\n" + "Usuario: " + usuario.getNombre() + " "
                    + usuario.getApellido() + "\n" + "Fecha de emisión de la orden: " + fechaActual, "Nueva orden para el servicio de: " + tipoServicio);

            redirectAttributes.addFlashAttribute("exito", "La orden ha sido enviada. El técnico encargado la evaluará a la brevedad.");

            return "redirect:/user/order_service";

        } catch (MyException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("tipoServicio", tipoServicio);
            modelo.put("detalleOrden", detalleOrden);
            return "redirect:/new_order/{dni}";

        }

    }
}
