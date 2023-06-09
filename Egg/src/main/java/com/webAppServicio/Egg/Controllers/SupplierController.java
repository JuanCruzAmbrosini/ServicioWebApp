package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.DateConverter.DateConverter;
import com.webAppServicio.Egg.Email.EnvioDeCorreo;
import com.webAppServicio.Egg.Entities.*;
import com.webAppServicio.Egg.Enums.EstatusOrden;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Services.OrderServiceServices;
import com.webAppServicio.Egg.Services.ServiceOfServices;
import com.webAppServicio.Egg.Services.SupplierService;

import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierS;

    @Autowired
    private ServiceOfServices serviceS;

    @Autowired
    private OrderServiceServices orderS;

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
        
        List<TechnicalService> servicios = serviceS.listarServicios();
        modelo.addAttribute("servicios", servicios);

        try {

            TechnicalService oficio = serviceS.buscarServicioPorTipo(tipoServicio);
            supplierS.crearProveedor(imagen, dni, matricula, nombre, apellido, telefono, email, password, password2, tipoServicio);
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
    public String listaOrdenPorProveedor(ModelMap modelo, HttpSession session) {

        Supplier usuario = (Supplier) session.getAttribute("usuariosession");
        List<OrderService> ordenes = new ArrayList<>();

        ordenes = orderS.listarOrdenesPorProveedorId(usuario.getDni());

        modelo.addAttribute("ordenes", ordenes);

        return "order_service_supplier.html";
    }

    @GetMapping("/order_cancel/{id}")
    public String cancelarOrdenSinCotizar(@PathVariable Integer id, RedirectAttributes redirectAttributes) throws MyException {
        OrderService order = orderS.getOne(id);

        EnvioDeCorreo edc = new EnvioDeCorreo();
        edc.transfer_to_email(order.getProveedor().getEmail(), "El proveedor: " + order.getProveedor().getNombre() + " "
                + order.getProveedor().getApellido() + " ha cancelado la orden pendiente (id N°= " + order.getId() + ") \n Gracias por utilizar nuestros servicios.", "Orden N°"
                + order.getId() + " cancelada.");

        orderS.eliminarOrden(id);

        redirectAttributes.addFlashAttribute("exito", "La orden ha sido cancelada con éxito.");

        return "redirect:/supplier/order_service";
    }

    @GetMapping("/order_cancel_quoted/{id}")
    public String cancelarOrdenSinCotizarCotizada(@PathVariable Integer id, RedirectAttributes redirectAttributes) throws MyException {
        OrderService order = orderS.getOne(id);

        EnvioDeCorreo edc = new EnvioDeCorreo();
        edc.transfer_to_email(order.getProveedor().getEmail(), "El proveedor: " + order.getProveedor().getNombre() + " "
                + order.getProveedor().getApellido() + " ha cancelado la orden pendiente (id N°= " + order.getId() + ") \n Gracias por utilizar nuestros servicios.", "Orden N°"
                + order.getId() + " cancelada.");

        orderS.eliminarOrden(id);

        redirectAttributes.addFlashAttribute("exito", "La orden ha sido cancelada con éxito.");

        return "redirect:/supplier/order_service_quoted";
    }

    @GetMapping("/order_service_quoted")
    public String listaOrdenPorProveedorCotizadas(ModelMap modelo, HttpSession session) {

        Supplier usuario = (Supplier) session.getAttribute("usuariosession");
        List<OrderService> ordenes = new ArrayList<>();

        ordenes = orderS.listarOrdenesPorProveedorId(usuario.getDni());

        modelo.addAttribute("ordenes", ordenes);

        return "order_service_supplier_quoted.html";
    }

    @GetMapping("/order_service_finally")
    public String listaOrdenPorProveedorFinalizadas(ModelMap modelo, HttpSession session) {

        Supplier usuario = (Supplier) session.getAttribute("usuariosession");
        List<OrderService> ordenes = new ArrayList<>();

        ordenes = orderS.listarOrdenesPorProveedorId(usuario.getDni());

        modelo.addAttribute("ordenes", ordenes);

        return "order_service_supplier_finally.html";
    }

    @GetMapping("/order_service_in_process")
    public String listaOrdenPorProveedorEnProceso(ModelMap modelo, HttpSession session) {

        Supplier usuario = (Supplier) session.getAttribute("usuariosession");
        List<OrderService> ordenes = new ArrayList<>();

        ordenes = orderS.listarOrdenesPorProveedorId(usuario.getDni());

        modelo.addAttribute("ordenes", ordenes);

        return "order_service_supplier_in_process.html";
    }

    @PostMapping("/order_finalization/{id}")
    public String aprovarOrden(@PathVariable Integer id, ModelMap modelo, HttpSession session, RedirectAttributes redirectAttributes) {

        OrderService orden = orderS.getOne(id);
        EnvioDeCorreo edc = new EnvioDeCorreo();
        Supplier usuario = (Supplier) session.getAttribute("usuariosession");
        Date fechaFinalizada = new Date();

        try {

            orderS.modificarOrdenConFechaFinalizada(orden.getId(), orden.getOficio().getTipoServicio(),
                    orden.getDetalleOrden(), orden.getPresupuesto(),
                    String.valueOf(EstatusOrden.FINALIZADA), orden.getFechaRecibida(), fechaFinalizada);

            edc.transfer_to_email(orden.getUsuario().getEmail(), "El técnico " + usuario.getNombre() + " "
                    + usuario.getApellido() + " ha finalizado su trabajo! Por favor, pásate por nuestra página para dejarle una "
                    + "reseña y así ayudar a los demás usuarios.\nEsperamos serte nuevamente de utilidad en el futuro!",
                    "Orden N° " + orden.getId() + "(" + orden.getOficio().getTipoServicio() + ") ha sido finalizada.");

            redirectAttributes.addFlashAttribute("exito", "La orden ha sido finalizada. Pronto el cliente le dejará una reseña en su cuenta!\nGracias por usar nuestros servicios!");

            return "redirect:/supplier/order_service_finally";

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error. Inténtelo nuevamente mas tarde! \nGracias por usar nuestros servicios!");
            return "redirect:/supplier/order_service_in_process";
        }

    }

    @GetMapping("/profile")
    public String perfilSupplier(HttpSession session, ModelMap modelo) {
        Person proveedor = (Person) session.getAttribute("usuariosession");
        modelo.addAttribute("proveedor", proveedor);
        return "profileSupplier.html";
    }

    @GetMapping("/profile_edit/{dni}")
    public String perfilSupplierEdit(@PathVariable String dni, ModelMap modelo) {
        Person proveedor = supplierS.getOne(dni);
        modelo.addAttribute("proveedor", proveedor);
        return "modification_supplier.html";
    }

    @PostMapping("/modification_profile/{dni}")
    public String perfilModificado(@PathVariable String dni, @RequestParam MultipartFile imagen, @RequestParam String matricula, @RequestParam String nombre,
            @RequestParam String apellido, @RequestParam("correoOculto") String email,
            @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, @RequestParam("oficioOculto") String tipoServicio,
            ModelMap modelo, HttpSession session, RedirectAttributes redirectAttributes) throws MyException {

        TechnicalService oficio = serviceS.buscarServicioPorTipo(tipoServicio);

        try {
            supplierS.modificarPerfil(imagen, dni, matricula, nombre, apellido, telefono, email, password, password2, tipoServicio);

            redirectAttributes.addFlashAttribute("exito", "Tu Perfil Se Actualizo Correctamente, Inicie Sesion Nuevamente Para Ver Los Cambios");
            return "redirect:/supplier/profile";

        } catch (MyException ex) {

            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            redirectAttributes.addFlashAttribute("nombre", nombre);
            redirectAttributes.addFlashAttribute("apellido", apellido);
            redirectAttributes.addFlashAttribute("oficio", tipoServicio);
            redirectAttributes.addFlashAttribute("telefono", telefono);
            redirectAttributes.addFlashAttribute("password", password);
            redirectAttributes.addFlashAttribute("password2", password2);

            return "redirect:/supplier/profile_edit/{dni}";

        }
    }

    @GetMapping("/order_response/{id}")
    public String cotizarOrden(ModelMap modelo, @PathVariable Integer id) {

        modelo.addAttribute("orden", orderS.getOne(id));

        return "order_response.html";

    }

    @PostMapping("/order_responsed/{id}")
    public String ordenCotizada(@PathVariable Integer id, @RequestParam String fecha_hora, @RequestParam double presupuesto, HttpSession session, ModelMap modelo, RedirectAttributes redirectAttributes) {

        DateConverter dc = new DateConverter();
        EnvioDeCorreo edc = new EnvioDeCorreo();
        Supplier proveedor = (Supplier) session.getAttribute("usuariosession");
        OrderService orden = orderS.getOne(id);
        Date fechaRecibida = dc.conversorFecha(fecha_hora);

        try {
            orderS.modificarOrden(orden.getId(), orden.getOficio().getTipoServicio(), orden.getDetalleOrden(), presupuesto, String.valueOf(EstatusOrden.COTIZADA), fechaRecibida);
            edc.transfer_to_email(orden.getUsuario().getEmail(), "La orden N° " + orden.getId() + " ya ha sido cotizada por el técnico " + proveedor.getNombre() + " "
                    + proveedor.getApellido() + " y está a la espera de su confirmación! \n Gracias por usar nuestros servicios!", "Orden N° " + orden.getId() + "("
                    + orden.getOficio().getTipoServicio() + ") ha sido cotizada.");

            redirectAttributes.addFlashAttribute("exito", "La orden ha sido cotizada y se le enviará la información al cliente.");

            return "redirect:/supplier/order_service_quoted";

        } catch (MyException e) {

            redirectAttributes.addFlashAttribute("error", "Ha habido un inconveniente a la hora de enviar la cotización. Revise los campos ingresados o inténtelo nuevamente más tarde.");
            return "redirect:/supplier/order_service";
        }

    }

    @GetMapping("/view_order/{id}")
    public String verOrden(@PathVariable Integer id, ModelMap modelo) {

        modelo.addAttribute("orden", orderS.getOne(id));

        return "view_order_service.html";
    }

}
