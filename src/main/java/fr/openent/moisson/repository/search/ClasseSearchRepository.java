package fr.openent.moisson.repository.search;

import fr.openent.moisson.domain.Classe;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Classe} entity.
 */
public interface ClasseSearchRepository extends ElasticsearchRepository<Classe, Long> {
}
