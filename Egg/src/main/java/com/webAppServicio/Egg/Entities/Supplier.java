package com.webAppServicio.Egg.Entities;

import com.webAppServicio.Egg.Enums.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
public class Supplier {

    @Id
    private String matricula;

    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String password;
    private String oficio;
    private double calificacion;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToOne
    private Image imagen;

    @OneToOne(mappedBy = "proveedor")
    private OrderService ordenServicio;

    @OneToOne(mappedBy = "proveedor")
    private Service servicios;
}
