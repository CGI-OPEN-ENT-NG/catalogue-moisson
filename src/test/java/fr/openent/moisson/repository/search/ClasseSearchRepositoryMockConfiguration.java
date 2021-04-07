package fr.openent.moisson.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ClasseSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ClasseSearchRepositoryMockConfiguration {

    @MockBean
    private ClasseSearchRepository mockClasseSearchRepository;

}
