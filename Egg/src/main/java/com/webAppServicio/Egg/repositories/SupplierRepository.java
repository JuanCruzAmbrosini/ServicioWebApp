/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webAppServicio.Egg.repositories;

import com.webAppServicio.Egg.Entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Juan Cruz
 */
public interface SupplierRepository extends JpaRepository <Supplier, String>{
    
     @Query("SELECT s FROM Supplier s WHERE s.nombre = :nombre")
    public String buscarProveedorPorNombre (@Param ("nombre") String nombre);
    
     @Query("SELECT s FROM Supplier s WHERE s.oficio = :oficio")
    public String buscarProveedorPorOficio (@Param ("oficio") String nombre);
    
    @Query("SELECT s FROM Supplier s WHERE s.apellido = :apellido")
    public String buscarProveedorPorApellido (@Param ("apellido") String nombre);
    
}
