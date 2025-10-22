package br.com.cambio_service.cambio.model;

import br.com.cambio_service.cambio.dto.CambioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cambio", schema = "cambio")
public class Cambio implements Serializable {

    @Serial
    private static final long serialVersionUID = -9135131851038755387L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "from_currency")
    private String from;

    @Column(name = "to_currency")
    private String to;

    @Column(name = "conversion_factor")
    private BigDecimal convertFactor;

    public CambioDTO dto() {
        CambioDTO dto = new CambioDTO();
        dto.setFrom(this.from);
        dto.setTo(this.to);
        dto.setConvertFactor(this.convertFactor);
        return dto;
    }

}
