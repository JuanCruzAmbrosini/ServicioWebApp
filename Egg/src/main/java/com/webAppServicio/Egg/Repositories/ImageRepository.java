package com.webAppServicio.Egg.Repositories;

import com.webAppServicio.Egg.Entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String>{
    
}
