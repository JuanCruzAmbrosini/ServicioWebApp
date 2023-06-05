package com.webAppServicio.Egg.Repositories;

import com.webAppServicio.Egg.Entities.OrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderService, Integer > {

    @Query("SELECT o FROM OrderService o WHERE o.usuario.dni = :dni")
    public List<OrderService> listarOrdenesPorClienteId(@Param("dni") String dni);

    @Query("SELECT o FROM OrderService o WHERE o.proveedor.dni = :dni")
    public List<OrderService> listarOrdenesPorProveedorId(@Param("dni") String dni);

}


