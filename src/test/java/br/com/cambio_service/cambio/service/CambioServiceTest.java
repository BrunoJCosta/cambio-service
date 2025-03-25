package br.com.cambio_service.cambio.service;

import br.com.cambio_service.cambio.dto.CambioDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.math.BigDecimal;
import java.util.stream.Stream;

@SpringBootTest
class CambioServiceTest {

    @Autowired
    private CambioService cambioService;

    @ParameterizedTest
    @MethodSource
    void findByFromAndTo(BigDecimal amount, String from, String to, BigDecimal expected) {
        CambioDTO dto = cambioService.findByFromAndTo(amount, from, to);
        Assertions.assertNotNull(dto);

        Assertions.assertEquals(expected, dto.getConvertValue());
    }

    private static Stream<Arguments> findByFromAndTo() {
        BigDecimal amount = new BigDecimal(7);

        return Stream.of(
                Arguments.of(amount, "USD", "BRL", new BigDecimal("40.11")),
                Arguments.of(amount, "USD", "EUR", new BigDecimal("5.88")),
                Arguments.of(amount, "USD", "GBP", new BigDecimal("5.11")),
                Arguments.of(amount, "USD", "ARS", new BigDecimal("647.92")),
                Arguments.of(amount, "USD", "COP", new BigDecimal("25655.00")),
                Arguments.of(amount, "USD", "MXN", new BigDecimal("141.05"))
        );
    }


}