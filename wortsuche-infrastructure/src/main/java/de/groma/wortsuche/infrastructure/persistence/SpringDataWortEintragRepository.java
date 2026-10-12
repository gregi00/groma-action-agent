package de.groma.wortsuche.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface SpringDataWortEintragRepository extends JpaRepository<WortEintragEntity, Long> {
    List<WortEintragEntity> findAllByOrderByWortNormalisiertAsc();

    Optional<WortEintragEntity> findByWortNormalisiert(String wortNormalisiert);
}
