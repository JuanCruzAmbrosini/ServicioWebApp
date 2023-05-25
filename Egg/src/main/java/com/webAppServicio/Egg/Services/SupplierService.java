package com.webAppServicio.Egg.Services;

import com.webAppServicio.Egg.Entities.Image;
import com.webAppServicio.Egg.Entities.Supplier;
import com.webAppServicio.Egg.Enums.Rol;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Repositories.SupplierRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierR;
    
    @Autowired
    private ImageService imagenS;

    @Transactional
    public void crearProveedor(MultipartFile imagen, String matricula, String nombre, String apellido,
            String telefono, String email, String password, String password2, String oficio) throws MyException {

        validarProveedor(matricula, nombre, apellido, telefono, email, password, password2, oficio);

        Supplier supplier = new Supplier();

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
    public void modificarPerfil(String matricula, String nombre, String apellido,
            String telefono, String email, String password, String password2, String oficio) throws MyException {

        validarProveedor(matricula, nombre, apellido, telefono, email, password, password2, oficio);

        Optional<Supplier> respuesta = supplierR.findById(matricula);
        if (respuesta.isPresent()) {

            Supplier supplier = respuesta.get();

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
    public void eliminarProveedor(String matricula) {

        supplierR.deleteById(matricula);

    }
    
    public List<Supplier> listarProveedores(){
        List <Supplier> listaProveedores = new ArrayList<>();
        
        listaProveedores = supplierR.findAll();
        
        return listaProveedores;
    }
    
    public Supplier getOne(String matricula){
        return supplierR.getOne(matricula);
    }

    public void validarProveedor(String matricula, String nombre, String apellido,
            String telefono, String email, String password, String password2, String oficio) throws MyException {

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
}
