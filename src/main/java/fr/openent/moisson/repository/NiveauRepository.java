package fr.openent.moisson.repository;

import fr.openent.moisson.domain.Niveau;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Niveau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NiveauRepository extends JpaRepository<Niveau, Long>, JpaSpecificationExecutor<Niveau> {
}
