package fr.openent.moisson.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link OffreSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class OffreSearchRepositoryMockConfiguration {

    @MockBean
    private OffreSearchRepository mockOffreSearchRepository;

}
