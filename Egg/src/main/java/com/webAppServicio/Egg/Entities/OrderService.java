package com.webAppServicio.Egg.Entities;

import com.webAppServicio.Egg.Enums.EstatusOrden;
import java.util.Date;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "technical_service_id")
    private TechnicalService Oficio;

    private String detalleOrden;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    private Date fechaRecibida;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client usuario;

    @ManyToOne()
    @JoinColumn(name = "supplier_id")
    private Supplier proveedor;

    @Enumerated(EnumType.STRING)
    private EstatusOrden estatusOrden;
}
