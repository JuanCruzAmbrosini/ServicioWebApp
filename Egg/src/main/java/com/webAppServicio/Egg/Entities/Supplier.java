package com.webAppServicio.Egg.Entities;

import java.util.List;
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
public class Supplier extends Person {

    private String matricula;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calificacion> calificaciones;

    @ManyToOne
    @JoinColumn(name = "technical_service_id")
    private TechnicalService oficio;

}
