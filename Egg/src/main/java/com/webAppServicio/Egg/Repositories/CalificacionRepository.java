package com.webAppServicio.Egg.Repositories;

import com.webAppServicio.Egg.Entities.Calificacion;
import com.webAppServicio.Egg.Entities.OrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    @Query("SELECT c FROM Calificacion c WHERE c.proveedor.dni = :dni")
    public String buscarCalificacionPorProveedor(@Param("dni") String dni);

}
