
package com.webAppServicio.Egg.Controllers;

import com.webAppServicio.Egg.Entities.Supplier;
import com.webAppServicio.Egg.Entities.Client;
import com.webAppServicio.Egg.Entities.TechnicalService;
import com.webAppServicio.Egg.Services.ServiceOfServices;
import com.webAppServicio.Egg.Services.SupplierService;
import com.webAppServicio.Egg.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImageController {
    @Autowired
    private UserService userS;
    
    @Autowired
    private SupplierService supplierS;
    
    @Autowired
    private ServiceOfServices serviceS;
    
    @GetMapping("/perfilUser/{dni}")
    public ResponseEntity<byte[]> imageUser(@PathVariable String dni){
        Client usuarios = userS.getOne(dni);
        
        byte[] imagen = usuarios.getImagen().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    @GetMapping("/perfilSupplier/{matricula}")
    public ResponseEntity<byte[]> imageSupplier(@PathVariable String matricula){
        Supplier proveedor = supplierS.getOne(matricula);
        
        byte[] imagen = proveedor.getImagen().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    @GetMapping("/imagenService/{id}")
    public ResponseEntity<byte[]> imageService(@PathVariable String id){
        TechnicalService servicio = serviceS.getOne(id);
        
        byte[] imagen = servicio.getImagen().getContenido();
        
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.IMAGE_JPEG);
        
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
}