package com.webAppServicio.Egg.Repositories;

import com.webAppServicio.Egg.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    
    @Query("SELECT u FROM User u WHERE u.nombre = :nombre")
    public String buscarUsuarioPorNombre (@Param ("nombre") String nombre);
    
    @Query("SELECT u FROM User u WHERE u.apellido = :apellido")
    public String buscarUsuarioPorApellido (@Param ("apellido") String nombre);
    
     @Query("SELECT u FROM User u WHERE u.direccion = :direccion")
    public String buscarUsuarioPorDireccion (@Param ("direccion") String direccion);
    
}
