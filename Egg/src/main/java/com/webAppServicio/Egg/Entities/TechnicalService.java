package com.webAppServicio.Egg.Entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
public class TechnicalService {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String tipoServicio;
    private String detalle;
    private String caracteristicas;
    
    @OneToOne
    private Image imagen;
    
    @OneToOne
    private Client usuario;
    
    @OneToMany
    private List <Supplier> proveedores;

}
