package com.webAppServicio.Egg.Repositories;

import com.webAppServicio.Egg.Entities.TechnicalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<TechnicalService, String> {
    
}
