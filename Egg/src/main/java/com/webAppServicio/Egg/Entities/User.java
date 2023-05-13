package com.webAppServicio.Egg.Entities;

import com.webAppServicio.Egg.Enums.Rol;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
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
public class User {

    @Id
    private String dni;
    
    private String nombre;
    private String apellido;
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
    
    @OneToMany(mappedBy = "usuario")
    private List<Service> servicios;
    
    @OneToMany(mappedBy = "usuario")
    private List<OrderService> ordenServicios;

}
