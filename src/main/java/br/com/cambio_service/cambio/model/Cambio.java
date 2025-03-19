package br.com.cambio_service.cambio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cambio", schema = "cambio")
public class Cambio {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "from_currency")
    private String from;

    @Column(name = "to_currency")
    private String to;

    @Column(name = "conversion_factor")
    private BigDecimal convertFactor;

    @Transient
    private BigDecimal convertValue;

    @Transient
    private String environment;
}
