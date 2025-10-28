package br.com.cambio_service.cambio.configuration.redis;

import java.time.Duration;
import java.util.List;

public class CacheName {

    public static final String CAMBIO_ALL = "cambio_all";
    public static final String CAMBIO_BY_FROM_AND_TO = "cambio_by_from_to";

    static List<RedisDTO> cache() {
        return List.of(
                new RedisDTO(CAMBIO_ALL, Duration.ofMinutes(30)),
                new RedisDTO(CAMBIO_BY_FROM_AND_TO, Duration.ofMinutes(30))
        );
    }

}