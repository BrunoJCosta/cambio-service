package br.com.cambio_service.cambio.repository;

import br.com.cambio_service.cambio.model.Cambio;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static br.com.cambio_service.cambio.configuration.redis.CacheName.CAMBIO_ALL;
import static br.com.cambio_service.cambio.configuration.redis.CacheName.CAMBIO_BY_FROM_AND_TO;

@Repository
public interface CambioRepository extends JpaRepository<Cambio, Long> {

    @Cacheable(value = CAMBIO_BY_FROM_AND_TO, key = "#from + ':' + #to")
    Optional<Cambio> findByFromAndTo(String from, String to);

    @Query("select c.to from Cambio c")
    @Cacheable(CAMBIO_ALL)
    List<String> findToBy();
}