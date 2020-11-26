package fr.openent.moisson.service.impl;

import fr.openent.moisson.service.TvaService;
import fr.openent.moisson.domain.Tva;
import fr.openent.moisson.repository.TvaRepository;
import fr.openent.moisson.repository.search.TvaSearchRepository;
import fr.openent.moisson.service.dto.TvaDTO;
import fr.openent.moisson.service.mapper.TvaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Tva}.
 */
@Service
@Transactional
public class TvaServiceImpl implements TvaService {

    private final Logger log = LoggerFactory.getLogger(TvaServiceImpl.class);

    private final TvaRepository tvaRepository;

    private final TvaMapper tvaMapper;

    private final TvaSearchRepository tvaSearchRepository;

    public TvaServiceImpl(TvaRepository tvaRepository, TvaMapper tvaMapper, TvaSearchRepository tvaSearchRepository) {
        this.tvaRepository = tvaRepository;
        this.tvaMapper = tvaMapper;
        this.tvaSearchRepository = tvaSearchRepository;
    }

    @Override
    public TvaDTO save(TvaDTO tvaDTO) {
        log.debug("Request to save Tva : {}", tvaDTO);
        Tva tva = tvaMapper.toEntity(tvaDTO);
        tva = tvaRepository.save(tva);
        TvaDTO result = tvaMapper.toDto(tva);
        tvaSearchRepository.save(tva);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TvaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tvas");
        return tvaRepository.findAll(pageable)
            .map(tvaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TvaDTO> findOne(Long id) {
        log.debug("Request to get Tva : {}", id);
        return tvaRepository.findById(id)
            .map(tvaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tva : {}", id);
        tvaRepository.deleteById(id);
        tvaSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TvaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tvas for query {}", query);
        return tvaSearchRepository.search(queryStringQuery(query), pageable)
            .map(tvaMapper::toDto);
    }
}
