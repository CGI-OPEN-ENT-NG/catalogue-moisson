package fr.openent.moisson.service.impl;

import fr.openent.moisson.service.ArticlePapierService;
import fr.openent.moisson.domain.ArticlePapier;
import fr.openent.moisson.repository.ArticlePapierRepository;
import fr.openent.moisson.repository.DisponibiliteRepository;
import fr.openent.moisson.repository.search.ArticlePapierSearchRepository;
import fr.openent.moisson.service.dto.ArticlePapierDTO;
import fr.openent.moisson.service.mapper.ArticlePapierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ArticlePapier}.
 */
@Service
@Transactional
public class ArticlePapierServiceImpl implements ArticlePapierService {

    private final Logger log = LoggerFactory.getLogger(ArticlePapierServiceImpl.class);

    private final ArticlePapierRepository articlePapierRepository;

    private final ArticlePapierMapper articlePapierMapper;

    private final ArticlePapierSearchRepository articlePapierSearchRepository;

    private final DisponibiliteRepository disponibiliteRepository;

    public ArticlePapierServiceImpl(ArticlePapierRepository articlePapierRepository, ArticlePapierMapper articlePapierMapper, ArticlePapierSearchRepository articlePapierSearchRepository, DisponibiliteRepository disponibiliteRepository) {
        this.articlePapierRepository = articlePapierRepository;
        this.articlePapierMapper = articlePapierMapper;
        this.articlePapierSearchRepository = articlePapierSearchRepository;
        this.disponibiliteRepository = disponibiliteRepository;
    }

    @Override
    public ArticlePapierDTO save(ArticlePapierDTO articlePapierDTO) {
        log.debug("Request to save ArticlePapier : {}", articlePapierDTO);
        ArticlePapier articlePapier = articlePapierMapper.toEntity(articlePapierDTO);
        Long disponibiliteId = articlePapierDTO.getDisponibiliteId();
        disponibiliteRepository.findById(disponibiliteId).ifPresent(articlePapier::disponibilite);
        articlePapier = articlePapierRepository.save(articlePapier);
        ArticlePapierDTO result = articlePapierMapper.toDto(articlePapier);
        articlePapierSearchRepository.save(articlePapier);
        return result;
    }

    @Override
    public ArticlePapier save(ArticlePapier articlePapier) {
        log.debug("Request to save ArticlePapier : {}", articlePapier);
        articlePapier = articlePapierRepository.save(articlePapier);
        articlePapierSearchRepository.save(articlePapier);
        return articlePapier;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticlePapierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArticlePapiers");
        return articlePapierRepository.findAll(pageable)
            .map(articlePapierMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ArticlePapierDTO> findOneDTO(Long id) {
        log.debug("Request to get ArticlePapier : {}", id);
        return articlePapierRepository.findById(id)
            .map(articlePapierMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArticlePapier> findOne(Long id) {
        log.debug("Request to get ArticlePapier : {}", id);
        return articlePapierRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArticlePapier : {}", id);
        articlePapierRepository.deleteById(id);
        articlePapierSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticlePapierDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ArticlePapiers for query {}", query);
        return articlePapierSearchRepository.search(queryStringQuery(query), pageable)
            .map(articlePapierMapper::toDto);
    }
}
