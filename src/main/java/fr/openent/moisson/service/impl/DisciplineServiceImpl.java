package fr.openent.moisson.service.impl;

import fr.openent.moisson.service.DisciplineService;
import fr.openent.moisson.domain.Discipline;
import fr.openent.moisson.repository.DisciplineRepository;
import fr.openent.moisson.repository.search.DisciplineSearchRepository;
import fr.openent.moisson.service.dto.DisciplineDTO;
import fr.openent.moisson.service.mapper.DisciplineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Discipline}.
 */
@Service
@Transactional
public class DisciplineServiceImpl implements DisciplineService {

    private final Logger log = LoggerFactory.getLogger(DisciplineServiceImpl.class);

    private final DisciplineRepository disciplineRepository;

    private final DisciplineMapper disciplineMapper;

    private final DisciplineSearchRepository disciplineSearchRepository;

    public DisciplineServiceImpl(DisciplineRepository disciplineRepository, DisciplineMapper disciplineMapper, DisciplineSearchRepository disciplineSearchRepository) {
        this.disciplineRepository = disciplineRepository;
        this.disciplineMapper = disciplineMapper;
        this.disciplineSearchRepository = disciplineSearchRepository;
    }

    @Override
    public DisciplineDTO save(DisciplineDTO disciplineDTO) {
        log.debug("Request to save Discipline : {}", disciplineDTO);
        Discipline discipline = disciplineMapper.toEntity(disciplineDTO);
        discipline = disciplineRepository.save(discipline);
        DisciplineDTO result = disciplineMapper.toDto(discipline);
        disciplineSearchRepository.save(discipline);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisciplineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Disciplines");
        return disciplineRepository.findAll(pageable)
            .map(disciplineMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DisciplineDTO> findOne(Long id) {
        log.debug("Request to get Discipline : {}", id);
        return disciplineRepository.findById(id)
            .map(disciplineMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discipline : {}", id);
        disciplineRepository.deleteById(id);
        disciplineSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DisciplineDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Disciplines for query {}", query);
        return disciplineSearchRepository.search(queryStringQuery(query), pageable)
            .map(disciplineMapper::toDto);
    }
}
