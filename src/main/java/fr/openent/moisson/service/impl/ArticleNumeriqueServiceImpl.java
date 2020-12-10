package fr.openent.moisson.service.impl;

import fr.openent.moisson.service.ArticleNumeriqueService;
import fr.openent.moisson.domain.ArticleNumerique;
import fr.openent.moisson.repository.ArticleNumeriqueRepository;
import fr.openent.moisson.repository.DisponibiliteRepository;
import fr.openent.moisson.repository.search.ArticleNumeriqueSearchRepository;
import fr.openent.moisson.service.dto.ArticleNumeriqueDTO;
import fr.openent.moisson.service.mapper.ArticleNumeriqueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ArticleNumerique}.
 */
@Service
@Transactional
public class ArticleNumeriqueServiceImpl implements ArticleNumeriqueService {

    private final Logger log = LoggerFactory.getLogger(ArticleNumeriqueServiceImpl.class);

    private final ArticleNumeriqueRepository articleNumeriqueRepository;

    private final ArticleNumeriqueMapper articleNumeriqueMapper;

    private final ArticleNumeriqueSearchRepository articleNumeriqueSearchRepository;

    private final DisponibiliteRepository disponibiliteRepository;

    public ArticleNumeriqueServiceImpl(ArticleNumeriqueRepository articleNumeriqueRepository, ArticleNumeriqueMapper articleNumeriqueMapper, ArticleNumeriqueSearchRepository articleNumeriqueSearchRepository, DisponibiliteRepository disponibiliteRepository) {
        this.articleNumeriqueRepository = articleNumeriqueRepository;
        this.articleNumeriqueMapper = articleNumeriqueMapper;
        this.articleNumeriqueSearchRepository = articleNumeriqueSearchRepository;
        this.disponibiliteRepository = disponibiliteRepository;
    }

    @Override
    public ArticleNumeriqueDTO save(ArticleNumeriqueDTO articleNumeriqueDTO) {
        log.debug("Request to save ArticleNumerique : {}", articleNumeriqueDTO);
        ArticleNumerique articleNumerique = articleNumeriqueMapper.toEntity(articleNumeriqueDTO);
        Long disponibiliteId = articleNumeriqueDTO.getDisponibiliteId();
        disponibiliteRepository.findById(disponibiliteId).ifPresent(articleNumerique::disponibilite);
        articleNumerique = articleNumeriqueRepository.save(articleNumerique);
        ArticleNumeriqueDTO result = articleNumeriqueMapper.toDto(articleNumerique);
        articleNumeriqueSearchRepository.save(articleNumerique);
        return result;
    }

    @Override
    public ArticleNumerique save(ArticleNumerique articleNumerique) {
        log.debug("Request to save ArticleNumerique : {}", articleNumerique);
        articleNumerique = articleNumeriqueRepository.save(articleNumerique);
        articleNumeriqueRepository.save(articleNumerique);
        return articleNumerique;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleNumeriqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArticleNumeriques");
        return articleNumeriqueRepository.findAll(pageable)
            .map(articleNumeriqueMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleNumeriqueDTO> findOne(Long id) {
        log.debug("Request to get ArticleNumerique : {}", id);
        return articleNumeriqueRepository.findById(id)
            .map(articleNumeriqueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArticleNumerique : {}", id);
        articleNumeriqueRepository.deleteById(id);
        articleNumeriqueSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleNumeriqueDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ArticleNumeriques for query {}", query);
        return articleNumeriqueSearchRepository.search(queryStringQuery(query), pageable)
            .map(articleNumeriqueMapper::toDto);
    }
}
