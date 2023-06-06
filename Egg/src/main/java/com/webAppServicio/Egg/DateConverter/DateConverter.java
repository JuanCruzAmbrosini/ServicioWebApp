package com.webAppServicio.Egg.DateConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    public Date conversorFecha (String fechaStr) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date fecha;

        try {
            fecha = dateFormat.parse(fechaStr);
            return fecha;
            // Aquí puedes hacer uso de la variable 'fecha' de tipo Date
        } catch (ParseException e) {
            // Manejo de errores en caso de que la fecha no sea válida
            e.printStackTrace();
            return null;
        }

    }

}
