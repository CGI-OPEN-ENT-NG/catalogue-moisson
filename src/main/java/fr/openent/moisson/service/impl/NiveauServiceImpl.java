package fr.openent.moisson.service.impl;

import fr.openent.moisson.service.NiveauService;
import fr.openent.moisson.domain.Niveau;
import fr.openent.moisson.repository.NiveauRepository;
import fr.openent.moisson.repository.search.NiveauSearchRepository;
import fr.openent.moisson.service.dto.NiveauDTO;
import fr.openent.moisson.service.mapper.NiveauMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Niveau}.
 */
@Service
@Transactional
public class NiveauServiceImpl implements NiveauService {

    private final Logger log = LoggerFactory.getLogger(NiveauServiceImpl.class);

    private final NiveauRepository niveauRepository;

    private final NiveauMapper niveauMapper;

    private final NiveauSearchRepository niveauSearchRepository;

    public NiveauServiceImpl(NiveauRepository niveauRepository, NiveauMapper niveauMapper, NiveauSearchRepository niveauSearchRepository) {
        this.niveauRepository = niveauRepository;
        this.niveauMapper = niveauMapper;
        this.niveauSearchRepository = niveauSearchRepository;
    }

    @Override
    public NiveauDTO save(NiveauDTO niveauDTO) {
        log.debug("Request to save Niveau : {}", niveauDTO);
        Niveau niveau = niveauMapper.toEntity(niveauDTO);
        niveau = niveauRepository.save(niveau);
        NiveauDTO result = niveauMapper.toDto(niveau);
        niveauSearchRepository.save(niveau);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NiveauDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Niveaus");
        return niveauRepository.findAll(pageable)
            .map(niveauMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NiveauDTO> findOne(Long id) {
        log.debug("Request to get Niveau : {}", id);
        return niveauRepository.findById(id)
            .map(niveauMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Niveau : {}", id);
        niveauRepository.deleteById(id);
        niveauSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NiveauDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Niveaus for query {}", query);
        return niveauSearchRepository.search(queryStringQuery(query), pageable)
            .map(niveauMapper::toDto);
    }
}
