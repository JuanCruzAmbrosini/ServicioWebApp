package com.webAppServicio.Egg.Entities;

import java.util.List;
import javax.persistence.Entity;
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
public class Client extends Person {

    private String barrio;
    private String sexo;
    private String direccion;

    @Lob
    private String comentario;

    @OneToOne
    private Image imagen;

}
