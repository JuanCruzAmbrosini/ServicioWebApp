package com.webAppServicio.Egg.Entities;

import java.util.List;
import javax.persistence.Entity;
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
    private String oficio;
    private double calificacion;
    
    @OneToOne
    private Image imagen;
    
    @OneToOne
    private TechnicalService servicio;
    
    @OneToMany
    private List<OrderService> ordenes;
}
