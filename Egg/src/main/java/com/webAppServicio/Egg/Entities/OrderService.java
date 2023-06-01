package com.webAppServicio.Egg.Entities;

import com.webAppServicio.Egg.Enums.EstatusOrden;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

    private String detalleorden;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    private Date fechaRecibida;

    @ManyToOne
    private Client usuario;

    @OneToOne()
    private Supplier proveedor;

    @Enumerated(EnumType.STRING)
    private EstatusOrden estatusOrden;
}
