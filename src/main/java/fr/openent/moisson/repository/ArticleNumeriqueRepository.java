package fr.openent.moisson.repository;

import fr.openent.moisson.domain.ArticleNumerique;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArticleNumerique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleNumeriqueRepository extends JpaRepository<ArticleNumerique, Long>, JpaSpecificationExecutor<ArticleNumerique> {
}
