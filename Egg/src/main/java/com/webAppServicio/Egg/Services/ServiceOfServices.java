package com.webAppServicio.Egg.Services;

import com.webAppServicio.Egg.Entities.Image;
import com.webAppServicio.Egg.Entities.TechnicalService;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Repositories.TechnicalServiceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServiceOfServices {

    @Autowired
    private TechnicalServiceRepository servicioR;

    @Autowired
    private ImageService imageS;
    
    @Transactional
    public void crearServicio(String tipoServicio, String detalle, String caracteristicas, MultipartFile imagen) throws MyException {

        validar(tipoServicio, detalle, caracteristicas);

        TechnicalService servicioTecnico = new TechnicalService();

        servicioTecnico.setTipoServicio(tipoServicio);

        servicioTecnico.setDetalle(detalle);

        servicioTecnico.setCaracteristicas(caracteristicas);

        Image image = imageS.guardar(imagen);

        servicioTecnico.setImagen(image);

        servicioR.save(servicioTecnico);

    }

    public List<TechnicalService> listarServicios() {

        List<TechnicalService> listaServicios = new ArrayList<>();

        listaServicios = servicioR.findAll();

        return listaServicios;

    }

    @Transactional
    public void modificarServicio(String id, String tipoServicio, String detalle, String caracteristicas,  MultipartFile imagen) throws MyException {

        validar(tipoServicio, detalle, caracteristicas);

        Optional<TechnicalService> respuesta = servicioR.findById(id);

        if (respuesta.isPresent()) {

            TechnicalService servicioTecnico = respuesta.get();

            servicioTecnico.setTipoServicio(tipoServicio);

            servicioTecnico.setDetalle(detalle);

            servicioTecnico.setCaracteristicas(caracteristicas);

            Image image = imageS.actualizar(imagen, id);

            servicioTecnico.setImagen(image);

            servicioR.save(servicioTecnico);
        }
    }

    public TechnicalService getOne(String id) {
        return servicioR.getOne(id);
    }

    public TechnicalService buscarServicioPorTipo(String tipoDeServicio) {

        return servicioR.buscarServicioPorTipo(tipoDeServicio);
    }

    @Transactional
    public void eliminarServicio(String id) {    
        servicioR.deleteById(id);
    }

    public void validar(String tipoServicio, String detalle, String caracteristicas) throws MyException {
        if (tipoServicio == null || tipoServicio.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo de tipo de Servicio. Por favor, inténtelo nuevamente.");

        }

        if (detalle == null || detalle.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo detalle. Por favor, inténtelo nuevamente.");

        }

        if (caracteristicas == null || caracteristicas.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo de caracteristicas. Por favor, inténtelo nuevamente.");

        }

    }

}
