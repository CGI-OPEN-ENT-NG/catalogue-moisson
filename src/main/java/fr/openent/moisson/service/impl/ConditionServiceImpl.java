package fr.openent.moisson.service.impl;

import fr.openent.moisson.domain.Condition;
import fr.openent.moisson.repository.ConditionRepository;
import fr.openent.moisson.repository.search.ConditionSearchRepository;
import fr.openent.moisson.service.ConditionService;
import fr.openent.moisson.service.dto.ConditionDTO;
import fr.openent.moisson.service.mapper.ConditionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Condition}.
 */
@Service
@Transactional
public class ConditionServiceImpl implements ConditionService {

    private final Logger log = LoggerFactory.getLogger(ConditionServiceImpl.class);

    private final ConditionRepository conditionRepository;

    private final ConditionMapper conditionMapper;

    private final ConditionSearchRepository conditionSearchRepository;

    public ConditionServiceImpl(ConditionRepository conditionRepository, ConditionMapper conditionMapper, ConditionSearchRepository conditionSearchRepository) {
        this.conditionRepository = conditionRepository;
        this.conditionMapper = conditionMapper;
        this.conditionSearchRepository = conditionSearchRepository;
    }

    @Override
    public ConditionDTO save(ConditionDTO conditionDTO) {
        log.debug("Request to save Condition : {}", conditionDTO);
        Condition condition = conditionMapper.toEntity(conditionDTO);
        condition = conditionRepository.save(condition);
        ConditionDTO result = conditionMapper.toDto(condition);
        conditionSearchRepository.save(condition);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConditionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Conditions");
        return conditionRepository.findAll(pageable)
            .map(conditionMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ConditionDTO> findOne(Long id) {
        log.debug("Request to get Condition : {}", id);
        return conditionRepository.findById(id)
            .map(conditionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Condition : {}", id);
        conditionRepository.deleteById(id);
        conditionSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConditionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Conditions for query {}", query);
        return conditionSearchRepository.search(queryStringQuery(query), pageable)
            .map(conditionMapper::toDto);
    }
}
