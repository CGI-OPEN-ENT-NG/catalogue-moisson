package fr.openent.moisson.repository;

import fr.openent.moisson.domain.ArticlePapier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Spring Data  repository for the ArticlePapier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticlePapierRepository extends JpaRepository<ArticlePapier, Long>, JpaSpecificationExecutor<ArticlePapier> {

    Optional<ArticlePapier> findByEan(String ean);

    @Transactional
    void deleteByBookseller(String bookseller);
}
