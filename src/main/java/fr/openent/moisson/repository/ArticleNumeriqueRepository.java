package fr.openent.moisson.repository;

import fr.openent.moisson.domain.ArticleNumerique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the ArticleNumerique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleNumeriqueRepository extends JpaRepository<ArticleNumerique, Long>, JpaSpecificationExecutor<ArticleNumerique> {

    Optional<ArticleNumerique> findByEan(String ean);
}
