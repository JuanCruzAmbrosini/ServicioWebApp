
package com.webAppServicio.Egg.Services;

import com.webAppServicio.Egg.Entities.Image;
import com.webAppServicio.Egg.Entities.User;
import com.webAppServicio.Egg.Enums.Rol;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UserService {

    @Autowired
    private UserRepository userR;
    
    @Autowired
    private ImageService imageS;

    @Transactional
    public void crearUsuario(MultipartFile imagen, String dni, String nombre, String apellido, String telefono, String direccion, String barrio, String email, String password, String password2, String sexo) throws MyException {

        validarUsuario(dni, nombre, apellido, telefono, direccion, barrio, email, password, password2, sexo);
        
        User usuario = new User();

        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setSexo(sexo);
        Image image = imageS.guardar(imagen);
        usuario.setImagen(image);
        usuario.setBarrio(barrio);
        usuario.setRol(Rol.USER);
        
        userR.save(usuario);

    }

    public List<User> listarUsuarios() {

        List<User> listaUsuarios = new ArrayList<>();

        listaUsuarios = userR.findAll();

        return listaUsuarios;
    }

    @Transactional
    public void modificarPerfil(String dni, String nombre, String apellido, String barrio, String telefono, String direccion, String email, String password, String password2, String sexo) throws MyException {

        validarUsuario(dni, nombre, apellido, telefono, direccion, barrio, email, password, password2, sexo);
        
        Optional<User> respuesta = userR.findById(dni);
        if (respuesta.isPresent()) {

            User usuario = respuesta.get();

            usuario.setDni(dni);
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setTelefono(telefono);
            usuario.setDireccion(direccion);
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setSexo(sexo);
            
            userR.save(usuario);
        }

    }
    
    public User getOne(String dni){
        return userR.getOne(dni);
    }
    
    @Transactional
    public void eliminarUsuario (String dni){
        
        userR.deleteById(dni);
  
    }
    
    public void validarUsuario (String dni, String nombre, String apellido, String telefono, String direccion, String barrio, String email, String password, String password2 , String sexo) throws MyException{
        
        if ( dni == null || dni.isEmpty()){
            
            throw new MyException("No se registró una entrada válida en el campo del DNI. Por favor, inténtelo nuevamente.");
            
        }
        
        if (nombre == null || nombre.isEmpty()){
            
            throw new MyException("No se registró una entrada válida en el campo del nombre. Por favor, inténtelo nuevamente.");
            
        }
        
        if (apellido == null || apellido.isEmpty()){
            
            throw new MyException("No se registró una entrada válida en el campo del Apellido. Por favor, inténtelo nuevamente.");
            
        }
        
        if (telefono == null || telefono.isEmpty()){
            
            throw new MyException("No se registró una entrada válida en el campo del teléfono. Por favor, inténtelo nuevamente.");
            
        }
        
        if (direccion == null || direccion.isEmpty()){
            
            throw new MyException("No se registró una entrada válida en el campo del la dirección. Por favor, inténtelo nuevamente.");
            
        }
        
        if (barrio == null || barrio.isEmpty()){
            
            throw new MyException("No se registró una entrada válida en el campo del barrio. Por favor, inténtelo nuevamente.");
            
        }
        
        if (email == null || email.isEmpty()){
            
            throw new MyException("No se registró una entrada válida en el campo del e-mail. Por favor, inténtelo nuevamente.");
            
        }
        
        if (password == null || password.isEmpty()){
            
            throw new MyException("No se registró una entrada válida en el campo de la contraseña. Por favor, inténtelo nuevamente.");
            
        }
        
        if (password2 == null || password2.isEmpty()){
            
            throw new MyException("No se registró una entrada válida en el campo de repetir contraseña. Por favor, inténtelo nuevamente.");
            
        }
        
        if (sexo == null || sexo.isEmpty()){
            
            throw new MyException("No se registró una entrada válida en el campo del sexo. Por favor, inténtelo nuevamente.");
            
        }
        
    }
    
}
