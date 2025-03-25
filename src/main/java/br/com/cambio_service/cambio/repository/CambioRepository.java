package br.com.cambio_service.cambio.repository;

import br.com.cambio_service.cambio.model.Cambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CambioRepository extends JpaRepository<Cambio, Long> {

    Optional<Cambio> findByFromAndTo(String from, String to);

    @Query("select c.to from Cambio c")
    List<String> findToBy();
}