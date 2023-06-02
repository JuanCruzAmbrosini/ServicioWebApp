package com.webAppServicio.Egg.Entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Supplier extends Person {

    private String matricula;

    private double calificacion;

    @OneToOne(mappedBy = "proveedor")
    private OrderService ordenServicio;

    @ManyToOne
    @JoinColumn(name = "technical_service_id")
    private TechnicalService oficio;

    @OneToMany
    private List<OrderService> listaOrdenes;
}
