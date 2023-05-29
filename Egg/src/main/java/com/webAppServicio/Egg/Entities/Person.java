/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Juan Cruz
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Person {

    @Id
    private String dni;

    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToOne
    private Image imagen;

}
