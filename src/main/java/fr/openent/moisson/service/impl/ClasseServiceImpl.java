package fr.openent.moisson.service.impl;

import fr.openent.moisson.service.ClasseService;
import fr.openent.moisson.domain.Classe;
import fr.openent.moisson.repository.ClasseRepository;
import fr.openent.moisson.repository.search.ClasseSearchRepository;
import fr.openent.moisson.service.dto.ClasseDTO;
import fr.openent.moisson.service.mapper.ClasseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Classe}.
 */
@Service
@Transactional
public class ClasseServiceImpl implements ClasseService {

    private final Logger log = LoggerFactory.getLogger(ClasseServiceImpl.class);

    private final ClasseRepository classeRepository;

    private final ClasseMapper classeMapper;

    private final ClasseSearchRepository classeSearchRepository;

    public ClasseServiceImpl(ClasseRepository classeRepository, ClasseMapper classeMapper, ClasseSearchRepository classeSearchRepository) {
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
        this.classeSearchRepository = classeSearchRepository;
    }

    @Override
    public ClasseDTO save(ClasseDTO classeDTO) {
        log.debug("Request to save Classe : {}", classeDTO);
        Classe classe = classeMapper.toEntity(classeDTO);
        classe = classeRepository.save(classe);
        ClasseDTO result = classeMapper.toDto(classe);
        classeSearchRepository.save(classe);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClasseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Classes");
        return classeRepository.findAll(pageable)
            .map(classeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ClasseDTO> findOne(Long id) {
        log.debug("Request to get Classe : {}", id);
        return classeRepository.findById(id)
            .map(classeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Classe : {}", id);
        classeRepository.deleteById(id);
        classeSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClasseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Classes for query {}", query);
        return classeSearchRepository.search(queryStringQuery(query), pageable)
            .map(classeMapper::toDto);
    }
}
