/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webAppServicio.Egg.Services;

import com.webAppServicio.Egg.Entities.Supplier;
import com.webAppServicio.Egg.Enums.Rol;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Repositories.SupplierRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Juan Cruz
 */
@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierR;

    @Transactional
    public void crearProveedor(String matricula, String nombre, String apellido,
            String telefono, String email, String password, String oficio) throws MyException {

        validarProveedor(matricula, nombre, apellido, telefono, email, password, oficio);
        
        Supplier supplier = new Supplier();

        supplier.setMatricula(matricula);
        supplier.setNombre(nombre);
        supplier.setApellido(apellido);
        supplier.setTelefono(telefono);
        supplier.setEmail(email);
        supplier.setPassword(password);
        supplier.setOficio(oficio);
        
        supplier.setRol(Rol.SUPPLIER);
        supplier.setCalificacion(0);

        supplierR.save(supplier);

    }

    @Transactional
    public void modificarPerfil(String matricula, String nombre, String apellido,
            String telefono, String email, String password, String oficio) throws MyException {

        validarProveedor(matricula, nombre, apellido, telefono, email, password, oficio);
        
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
    public void eliminarProveedor( String matricula){
        
        supplierR.deleteById(matricula);
        
    }
    
    public void validarProveedor(String matricula, String nombre, String apellido,
            String telefono, String email, String password, String oficio) throws MyException{
        
        if ( matricula == null || matricula.isEmpty() ){
            
            throw new MyException("No se registró una entrada válida en el campo de la matrícula. Por favor, inténtelo nuevamente.");
            
        }
        
        if ( nombre == null || nombre.isEmpty() ){
            
            throw new MyException("No se registró una entrada válida en el campo del nombre. Por favor, inténtelo nuevamente.");
            
        }
        
        if ( apellido == null || apellido.isEmpty() ){
            
            throw new MyException("No se registró una entrada válida en el campo del apellido. Por favor, inténtelo nuevamente.");
            
        }
        
        if ( telefono == null || telefono.isEmpty() ){
            
            throw new MyException("No se registró una entrada válida en el campo del teléfono. Por favor, inténtelo nuevamente.");
            
        }
        
        if ( email == null || email.isEmpty() ){
            
            throw new MyException("No se registró una entrada válida en el campo del e-mail. Por favor, inténtelo nuevamente.");
            
        }
        
        if ( password == null || password.isEmpty() ){
            
            throw new MyException("No se registró una entrada válida en el campo de la contraseña. Por favor, inténtelo nuevamente.");
            
        }
        
        if ( oficio == null || oficio.isEmpty() ){
            
            throw new MyException("No se registró una entrada válida en el campo del oficio. Por favor, inténtelo nuevamente.");
            
        }
        
    }
    
}
