package com.webAppServicio.Egg.Repositories;

import com.webAppServicio.Egg.Entities.Supplier;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository <Supplier, String>{
    
     @Query("SELECT s FROM Supplier s WHERE s.nombre = :nombre")
    public String buscarProveedorPorNombre (@Param ("nombre") String nombre);
    
     @Query("SELECT s FROM Supplier s WHERE s.email = :email")
    public Supplier buscarProveedorPorEmail (@Param ("email") String email);
    
     @Query("SELECT s FROM Supplier s WHERE s.oficio = :oficio")
    public List<Supplier> buscarProveedorPorOficio (@Param ("oficio") String nombre);
    
    @Query("SELECT s FROM Supplier s WHERE s.apellido = :apellido")
    public String buscarProveedorPorApellido (@Param ("apellido") String nombre);

}
