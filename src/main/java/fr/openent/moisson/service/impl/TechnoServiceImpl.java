package fr.openent.moisson.service.impl;

import fr.openent.moisson.domain.Techno;
import fr.openent.moisson.repository.TechnoRepository;
import fr.openent.moisson.repository.search.TechnoSearchRepository;
import fr.openent.moisson.service.TechnoService;
import fr.openent.moisson.service.dto.TechnoDTO;
import fr.openent.moisson.service.mapper.TechnoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Techno}.
 */
@Service
@Transactional
public class TechnoServiceImpl implements TechnoService {

    private final Logger log = LoggerFactory.getLogger(TechnoServiceImpl.class);

    private final TechnoRepository technoRepository;

    private final TechnoMapper technoMapper;

    private final TechnoSearchRepository technoSearchRepository;

    public TechnoServiceImpl(TechnoRepository technoRepository, TechnoMapper technoMapper, TechnoSearchRepository technoSearchRepository) {
        this.technoRepository = technoRepository;
        this.technoMapper = technoMapper;
        this.technoSearchRepository = technoSearchRepository;
    }

    @Override
    public TechnoDTO save(TechnoDTO technoDTO) {
        log.debug("Request to save Techno : {}", technoDTO);
        Techno techno = technoMapper.toEntity(technoDTO);
        techno = technoRepository.save(techno);
        TechnoDTO result = technoMapper.toDto(techno);
        technoSearchRepository.save(techno);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TechnoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Technos");
        return technoRepository.findAll(pageable)
            .map(technoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TechnoDTO> findOne(Long id) {
        log.debug("Request to get Techno : {}", id);
        return technoRepository.findById(id)
            .map(technoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Techno : {}", id);
        technoRepository.deleteById(id);
        technoSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TechnoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Technos for query {}", query);
        return technoSearchRepository.search(queryStringQuery(query), pageable)
            .map(technoMapper::toDto);
    }
}
