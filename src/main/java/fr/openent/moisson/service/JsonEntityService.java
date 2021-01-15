package fr.openent.moisson.service;

import java.io.IOException;
import java.io.InputStream;

/**s
 * Service Interface for managing Json File.
 */
public interface JsonEntityService {

    Integer jacksonToArticlePapier() throws IOException;

    Integer jacksonToArticleNumerique() throws IOException;

    InputStream getJsonFromUrl(String url) throws IOException;
}
