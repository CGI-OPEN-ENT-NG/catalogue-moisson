package fr.openent.moisson.repository;

import fr.openent.moisson.domain.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Condition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConditionRepository extends JpaRepository<Condition, Long>, JpaSpecificationExecutor<Condition> {
}
