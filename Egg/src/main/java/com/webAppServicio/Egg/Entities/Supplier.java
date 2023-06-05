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

    private double calificacion;

    @ManyToOne
    @JoinColumn(name = "technical_service_id")
    private TechnicalService oficio;

}
