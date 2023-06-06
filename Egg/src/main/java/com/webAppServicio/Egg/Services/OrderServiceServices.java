/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webAppServicio.Egg.Services;

import com.webAppServicio.Egg.Entities.Client;
import com.webAppServicio.Egg.Entities.OrderService;
import com.webAppServicio.Egg.Entities.Supplier;
import com.webAppServicio.Egg.Entities.TechnicalService;
import com.webAppServicio.Egg.Enums.EstatusOrden;
import com.webAppServicio.Egg.Exceptions.MyException;
import com.webAppServicio.Egg.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceServices {

    @Autowired
    private OrderRepository orderR;

    @Autowired
    private ServiceOfServices servicioS;

    @Transactional
    public void createOrder (String tipoServicio, String detalleOrden, Client usuario, Supplier proveedor) throws MyException {

        validarOrden(tipoServicio, detalleOrden);

        OrderService orden = new OrderService();
        TechnicalService oficio = servicioS.buscarServicioPorTipo(tipoServicio);
        Date fechaCreacion = new Date();

        orden.setOficio(oficio);
        orden.setDetalleOrden(detalleOrden);
        orden.setFechaCreacion(fechaCreacion);
        orden.setUsuario(usuario);
        orden.setProveedor(proveedor);
        orden.setEstatusOrden(EstatusOrden.ABIERTA);

        orderR.save(orden);

    }

    @Transactional
    public void modificarOrden (Integer id, String tipoServicio, String detalleOrden, double presupuesto, String estatusOrden, Date fechaRecibida){

        Optional<OrderService> respuesta = orderR.findById(id);

        if(respuesta.isPresent()) {

            OrderService orden = respuesta.get();
            TechnicalService oficio = servicioS.buscarServicioPorTipo(tipoServicio);

            orden.setOficio(oficio);
            orden.setDetalleOrden(detalleOrden);
            orden.setPresupuesto(presupuesto);
            orden.setEstatusOrden(EstatusOrden.valueOf(estatusOrden));
            orden.setFechaRecibida(fechaRecibida);

            orderR.save(orden);
        }
    }

    @Transactional
    public void eliminarOrden (Integer id){

        orderR.deleteById(id);

    }

    public OrderService getOne(Integer id){return orderR.getOne(id);}

    public List<OrderService> listarOrdenesPorClienteId (String dniCliente){

        List<OrderService> listaDeOrdenesDeUnCliente = new ArrayList<>();

        listaDeOrdenesDeUnCliente = orderR.listarOrdenesPorClienteId(dniCliente);

        return listaDeOrdenesDeUnCliente;
    }

    public List<OrderService> listarOrdenesPorProveedorId (String dniProveedor){

        List<OrderService> listaDeOrdenesDeUnProveedor = new ArrayList<>();

        listaDeOrdenesDeUnProveedor = orderR.listarOrdenesPorProveedorId(dniProveedor);

        return listaDeOrdenesDeUnProveedor;
    }

    public void validarOrden (String tipoServicio, String detalleOrden) throws MyException {

        if (tipoServicio == null || tipoServicio.isEmpty() || tipoServicio.equalsIgnoreCase("Selecciona un servicio a solicitar.")) {

            throw new MyException("No se registró una entrada válida en el campo del tipo de servicio. Por favor, inténtelo nuevamente.");

        }

        if (detalleOrden == null || detalleOrden.isEmpty()) {

            throw new MyException("No se registró una entrada válida en el campo del detalle de la orden. Por favor, inténtelo nuevamente.");

        }

    }

}
