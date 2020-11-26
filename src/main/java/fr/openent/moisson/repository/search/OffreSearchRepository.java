package fr.openent.moisson.repository.search;

import fr.openent.moisson.domain.Offre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Offre} entity.
 */
public interface OffreSearchRepository extends ElasticsearchRepository<Offre, Long> {
}
