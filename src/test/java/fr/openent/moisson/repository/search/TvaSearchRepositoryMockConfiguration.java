package fr.openent.moisson.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TvaSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TvaSearchRepositoryMockConfiguration {

    @MockBean
    private TvaSearchRepository mockTvaSearchRepository;

}
