package com.webAppServicio.Egg.Repositories;

import com.webAppServicio.Egg.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Client, String>{
    
    @Query("SELECT c FROM Client c WHERE c.nombre = :nombre")
    public Client buscarUsuarioPorNombre (@Param ("nombre") String nombre);
    
    @Query("SELECT c FROM Client c WHERE c.apellido = :apellido")
    public Client buscarUsuarioPorApellido (@Param ("apellido") String nombre);
    
     @Query("SELECT c FROM Client c WHERE c.direccion = :direccion")
    public Client buscarUsuarioPorDireccion (@Param ("direccion") String direccion);
    
@Query("SELECT c FROM Client c WHERE c.email = :email")
    public Client buscarUsuarioPorEmail (@Param ("email") String email);    
    
}
