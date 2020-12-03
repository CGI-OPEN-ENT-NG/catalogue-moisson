package fr.openent.moisson.repository;

import fr.openent.moisson.domain.Licence;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Licence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LicenceRepository extends JpaRepository<Licence, Long>, JpaSpecificationExecutor<Licence> {
}
