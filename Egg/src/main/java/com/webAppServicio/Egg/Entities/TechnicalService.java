package com.webAppServicio.Egg.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    
    @ManyToOne
    private Client usuario;
    
    @OneToOne
    private Supplier proveedor;

}
