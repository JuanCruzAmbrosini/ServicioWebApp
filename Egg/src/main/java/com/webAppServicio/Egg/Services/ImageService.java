package com.webAppServicio.Egg.Services;

import com.webAppServicio.Egg.Entities.Image;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Repositories.ImageRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    
    @Autowired
    private ImageRepository imagenRepositorio;
    
    public Image guardar(MultipartFile imagen) throws MyException{
        
        if (imagen != null) {
            try {
                Image image = new Image();
                
                image.setMime(imagen.getContentType());
                
                image.setNombre(imagen.getName());
                
                image.setContenido(imagen.getBytes());
                
                return imagenRepositorio.save(image);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    
    public Image actualizar(MultipartFile imagen, String id) throws MyException{
        if (imagen != null) {
            try {
                Image image = new Image();
                
                if (id != null) {
                    Optional<Image>respuesta = imagenRepositorio.findById(id);
                    
                    if (respuesta.isPresent()) {
                        image = respuesta.get();
                    }
                }
                
                image.setMime(imagen.getContentType());
                
                image.setNombre(imagen.getName());
                
                image.setContenido(imagen.getBytes());
                
                return imagenRepositorio.save(image);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}
