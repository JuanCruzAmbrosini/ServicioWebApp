package com.webAppServicio.Egg.Repositories;

import com.webAppServicio.Egg.Entities.TechnicalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalServiceRepository extends JpaRepository<TechnicalService, String> {

    @Query("SELECT t FROM  TechnicalService t WHERE t.tipoServicio = :tipoServicio")
    public TechnicalService buscarServicioPorTipo (@Param("tipoServicio") String tipoServicio);

}
