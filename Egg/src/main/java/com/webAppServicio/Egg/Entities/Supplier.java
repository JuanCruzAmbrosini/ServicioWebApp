
package com.webAppServicio.Egg.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String matricula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String password;
    private String oficio; 
    private String calificacion;
    
    @OneToOne
    private Image imagen;
}
