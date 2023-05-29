package com.webAppServicio.Egg.Services;

import com.webAppServicio.Egg.Entities.Image;
import com.webAppServicio.Egg.Entities.Supplier;
import com.webAppServicio.Egg.Enums.Rol;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Repositories.SupplierRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SupplierService implements UserDetailsService {

    @Autowired
    private SupplierRepository supplierR;

    @Autowired
    private ImageService imagenS;

    @Transactional
    public void crearProveedor(MultipartFile imagen,String dni, String matricula, String nombre, String apellido,
            String telefono, String email, String password, String password2, String oficio) throws MyException {

        validarProveedor(dni, matricula, nombre, apellido, telefono, email, password, password2, oficio);

        Supplier supplier = new Supplier();
        
        supplier.setDni(dni);
        supplier.setMatricula(matricula);
        supplier.setNombre(nombre);
        supplier.setApellido(apellido);
        supplier.setTelefono(telefono);
        supplier.setEmail(email);
        supplier.setPassword(new BCryptPasswordEncoder().encode(password));
        supplier.setOficio(oficio);
        Image image = imagenS.guardar(imagen);
        supplier.setImagen(image);
        supplier.setRol(Rol.SUPPLIER);
        supplier.setCalificacion(0);

        supplierR.save(supplier);

    }

    @Transactional
    public void modificarPerfil(String dni, String matricula, String nombre, String apellido,
            String telefono, String email, String password, String password2, String oficio) throws MyException {

        validarProveedor(dni, matricula, nombre, apellido, telefono, email, password, password2, oficio);

        Optional<Supplier> respuesta = supplierR.findById(dni);
        if (respuesta.isPresent()) {

            Supplier supplier = respuesta.get();
            
            supplier.setDni(dni);
            supplier.setMatricula(matricula);
            supplier.setNombre(nombre);
            supplier.setApellido(apellido);
            supplier.setTelefono(telefono);
            supplier.setEmail(email);
            supplier.setPassword(password);
            supplier.setOficio(oficio);

            supplierR.save(supplier);

        }
    }

    @Transactional
    public void eliminarProveedor(String dni) {

        supplierR.deleteById(dni);

    }

    public List<Supplier> listarProveedores() {
        List<Supplier> listaProveedores = new ArrayList<>();

        listaProveedores = supplierR.findAll();

        return listaProveedores;
    }

    public Supplier getOne(String matricula) {
        return supplierR.getOne(matricula);
    }

    public void validarProveedor(String dni, String matricula, String nombre, String apellido,
            String telefono, String email, String password, String password2, String oficio) throws MyException {

        if ( dni == null || dni.isEmpty()){
            
            throw new MyException("No se registró una entrada válida en el campo del DNI. Por favor, inténtelo nuevamente.");
            
        }
        
        if (matricula == null || matricula.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo de la matrícula. Por favor, inténtelo nuevamente.");

        }

        if (nombre == null || nombre.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo del nombre. Por favor, inténtelo nuevamente.");

        }

        if (apellido == null || apellido.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo del apellido. Por favor, inténtelo nuevamente.");

        }

        if (telefono == null || telefono.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo del teléfono. Por favor, inténtelo nuevamente.");

        }

        if (email == null || email.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo del e-mail. Por favor, inténtelo nuevamente.");

        }

        if (password == null || password.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo de la contraseña. Por favor, inténtelo nuevamente.");

        }

        if (!password2.equals(password)) {

            throw new MyException("Las contraseñas no coinciden, por favor, revise nuevamente los campos.");

        }

        if (oficio == null || oficio.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo del oficio. Por favor, inténtelo nuevamente.");

        }
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Supplier usuario = supplierR.buscarProveedorPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

}
