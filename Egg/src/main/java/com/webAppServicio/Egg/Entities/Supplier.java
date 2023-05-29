package com.webAppServicio.Egg.Entities;

import javax.persistence.Entity;
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
public class Supplier extends Person{
    
    private String matricula;
    private String oficio;
    private double calificacion;

    @OneToOne(mappedBy = "proveedor")
    private OrderService ordenServicio;

    @OneToOne(mappedBy = "proveedor")
    private TechnicalService servicios;
}
