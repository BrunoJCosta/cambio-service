package br.com.cambio_service.cambio.dto;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CambioDTO {

    private String from;
    private String to;
    private BigDecimal convertFactor;
    private BigDecimal convertValue;
    private String environment;

}
