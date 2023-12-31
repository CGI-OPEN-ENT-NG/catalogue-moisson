package fr.openent.moisson.repository;

import fr.openent.moisson.domain.Techno;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Techno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechnoRepository extends JpaRepository<Techno, Long>, JpaSpecificationExecutor<Techno> {
}
