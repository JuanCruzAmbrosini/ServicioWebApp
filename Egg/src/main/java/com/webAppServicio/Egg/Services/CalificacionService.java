package com.webAppServicio.Egg.Services;

import com.webAppServicio.Egg.Entities.Calificacion;
import com.webAppServicio.Egg.Entities.Supplier;
import com.webAppServicio.Egg.Repositories.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalificacionService {

    @Autowired
    CalificacionRepository calificacionR;

    @Transactional
    public Calificacion crearCalificacion(double valorCalificacion){

        Calificacion calificacion = new Calificacion();

        calificacion.setValorCalificacion(valorCalificacion);

        calificacionR.save(calificacion);

        return calificacion;
    }

}
