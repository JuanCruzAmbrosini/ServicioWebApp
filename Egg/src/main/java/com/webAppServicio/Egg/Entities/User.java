package com.webAppServicio.Egg.Entities;

import com.webAppServicio.Egg.Enums.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String dni;
    private String nombre;
    private String telefono;
    private String direccion;
    private String email;
    private String password;
    private String sexo;
    private String barrio;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Lob
    private String comentario;
    
    @OneToOne
    private Image imagen;

}