package br.com.cambio_service.cambio.controller;

import br.com.cambio_service.cambio.model.Cambio;
import br.com.cambio_service.cambio.model.CambioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cambio_server")
@RequiredArgsConstructor
public class CambioController {

    private final Environment environment;
    private final CambioRepository repository;

    @GetMapping("{amount}/{from}/{to}")
    public Cambio getCambio(@PathVariable BigDecimal amount,
                            @PathVariable String from,
                            @PathVariable String to) {

        String port = environment.getProperty("local.server.port");

        Cambio cambio = repository.findByFromAndTo(from, to)
                .orElseThrow(() -> new RuntimeException("cambio not found"));
        BigDecimal conversion = cambio.getConvertFactor().multiply(amount);


        log.info("valor convertido: {}", conversion);
        cambio.setConvertValue(conversion);
        cambio.setEnvironment(port);

        return cambio;
    }

    @GetMapping("/currency")
    public List<String> getCurrency() {
        return repository.findAll().stream().map(Cambio::getTo).toList();
    }

}
