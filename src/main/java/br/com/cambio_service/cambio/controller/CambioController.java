package br.com.cambio_service.cambio.controller;

import br.com.cambio_service.cambio.dto.CambioDTO;
import br.com.cambio_service.cambio.service.CambioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cambio-service")
@RequiredArgsConstructor
public class CambioController {

    private final CambioService service;

    @GetMapping("/{amount}/{from}/{to}")
    public CambioDTO getCambio(@PathVariable BigDecimal amount,
                            @PathVariable String from,
                            @PathVariable String to) {

        return service.findByFromAndTo(amount, from, to);
    }

    @GetMapping("/currency")
    public List<String> getCurrency() {
        return service.findCurrentAll();
    }

}