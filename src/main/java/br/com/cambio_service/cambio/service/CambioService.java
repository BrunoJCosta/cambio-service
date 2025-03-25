package br.com.cambio_service.cambio.service;

import br.com.cambio_service.cambio.dto.CambioDTO;
import br.com.cambio_service.cambio.model.Cambio;
import br.com.cambio_service.cambio.repository.CambioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CambioService {

    private final CambioRepository repository;
    private final Environment environment;

    public CambioDTO findByFromAndTo(BigDecimal amount, String from, String to) {

        Cambio cambio = repository.findByFromAndTo(from, to)
                .orElseThrow(() -> new RuntimeException("cambio not found"));
        BigDecimal conversion = cambio.getConvertFactor().multiply(amount);
        CambioDTO dto = cambio.dto();

        String port = environment.getProperty("local.server.port");
        dto.setConvertValue(conversion);
        dto.setEnvironment(port);
        return dto;
    }

    public List<String> findCurrentAll() {
        return repository.findToBy();
    }

}

