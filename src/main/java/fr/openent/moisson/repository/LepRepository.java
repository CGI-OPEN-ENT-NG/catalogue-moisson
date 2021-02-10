package fr.openent.moisson.repository;

import fr.openent.moisson.domain.Lep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Lep entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LepRepository extends JpaRepository<Lep, Long>, JpaSpecificationExecutor<Lep> {
}
