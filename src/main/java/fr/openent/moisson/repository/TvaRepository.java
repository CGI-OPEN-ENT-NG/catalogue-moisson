package fr.openent.moisson.repository;

import fr.openent.moisson.domain.Tva;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Tva entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TvaRepository extends JpaRepository<Tva, Long>, JpaSpecificationExecutor<Tva> {
}
