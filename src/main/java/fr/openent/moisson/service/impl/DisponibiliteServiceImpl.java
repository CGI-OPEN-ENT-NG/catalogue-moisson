package fr.openent.moisson.service.impl;

import fr.openent.moisson.service.DisponibiliteService;
import fr.openent.moisson.domain.Disponibilite;
import fr.openent.moisson.repository.DisponibiliteRepository;
import fr.openent.moisson.repository.search.DisponibiliteSearchRepository;
import fr.openent.moisson.service.dto.DisponibiliteDTO;
import fr.openent.moisson.service.mapper.DisponibiliteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Disponibilite}.
 */
@Service
@Transactional
public class DisponibiliteServiceImpl implements DisponibiliteService {

    private final Logger log = LoggerFactory.getLogger(DisponibiliteServiceImpl.class);

    private final DisponibiliteRepository disponibiliteRepository;

    private final DisponibiliteMapper disponibiliteMapper;

    private final DisponibiliteSearchRepository disponibiliteSearchRepository;

    public DisponibiliteServiceImpl(DisponibiliteRepository disponibiliteRepository, DisponibiliteMapper disponibiliteMapper, DisponibiliteSearchRepository disponibiliteSearchRepository) {
        this.disponibiliteRepository = disponibiliteRepository;
        this.disponibiliteMapper = disponibiliteMapper;
        this.disponibiliteSearchRepository = disponibiliteSearchRepository;
    }

    @Override
    public DisponibiliteDTO save(DisponibiliteDTO disponibiliteDTO) {
        log.debug("Request to save Disponibilite : {}", disponibiliteDTO);
        Disponibilite disponibilite = disponibiliteMapper.toEntity(disponibiliteDTO);
        disponibilite = disponibiliteRepository.save(disponibilite);
        DisponibiliteDTO result = disponibiliteMapper.toDto(disponibilite);
        disponibiliteSearchRepository.save(disponibilite);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisponibiliteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Disponibilites");
        return disponibiliteRepository.findAll(pageable)
            .map(disponibiliteMapper::toDto);
    }



    /**
     *  Get all the disponibilites where ArticlePapier is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<DisponibiliteDTO> findAllWhereArticlePapierIsNull() {
        log.debug("Request to get all disponibilites where ArticlePapier is null");
        return StreamSupport
            .stream(disponibiliteRepository.findAll().spliterator(), false)
            .filter(disponibilite -> disponibilite.getArticlePapier() == null)
            .map(disponibiliteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the disponibilites where ArticleNumerique is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<DisponibiliteDTO> findAllWhereArticleNumeriqueIsNull() {
        log.debug("Request to get all disponibilites where ArticleNumerique is null");
        return StreamSupport
            .stream(disponibiliteRepository.findAll().spliterator(), false)
            .filter(disponibilite -> disponibilite.getArticleNumerique() == null)
            .map(disponibiliteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DisponibiliteDTO> findOne(Long id) {
        log.debug("Request to get Disponibilite : {}", id);
        return disponibiliteRepository.findById(id)
            .map(disponibiliteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Disponibilite : {}", id);
        disponibiliteRepository.deleteById(id);
        disponibiliteSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisponibiliteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Disponibilites for query {}", query);
        return disponibiliteSearchRepository.search(queryStringQuery(query), pageable)
            .map(disponibiliteMapper::toDto);
    }
}
