package fr.openent.moisson.service.impl;

import fr.openent.moisson.service.LepService;
import fr.openent.moisson.domain.Lep;
import fr.openent.moisson.repository.LepRepository;
import fr.openent.moisson.repository.LicenceRepository;
import fr.openent.moisson.repository.search.LepSearchRepository;
import fr.openent.moisson.service.dto.LepDTO;
import fr.openent.moisson.service.mapper.LepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Lep}.
 */
@Service
@Transactional
public class LepServiceImpl implements LepService {

    private final Logger log = LoggerFactory.getLogger(LepServiceImpl.class);

    private final LepRepository lepRepository;

    private final LepMapper lepMapper;

    private final LepSearchRepository lepSearchRepository;

    private final LicenceRepository licenceRepository;

    public LepServiceImpl(LepRepository lepRepository, LepMapper lepMapper, LepSearchRepository lepSearchRepository, LicenceRepository licenceRepository) {
        this.lepRepository = lepRepository;
        this.lepMapper = lepMapper;
        this.lepSearchRepository = lepSearchRepository;
        this.licenceRepository = licenceRepository;
    }

    @Override
    public LepDTO save(LepDTO lepDTO) {
        log.debug("Request to save Lep : {}", lepDTO);
        Lep lep = lepMapper.toEntity(lepDTO);
        Long licenceId = lepDTO.getLicenceId();
        licenceRepository.findById(licenceId).ifPresent(lep::licence);
        lep = lepRepository.save(lep);
        LepDTO result = lepMapper.toDto(lep);
        lepSearchRepository.save(lep);
        return result;
    }

    @Override
    public Lep save(Lep lep) {
        log.debug("Request to save Lep : {}", lep);
        lep = lepRepository.save(lep);
        lepSearchRepository.save(lep);
        return lep;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Leps");
        return lepRepository.findAll(pageable)
            .map(lepMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LepDTO> findOne(Long id) {
        log.debug("Request to get Lep : {}", id);
        return lepRepository.findById(id)
            .map(lepMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lep : {}", id);
        lepRepository.deleteById(id);
        lepSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LepDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Leps for query {}", query);
        return lepSearchRepository.search(queryStringQuery(query), pageable)
            .map(lepMapper::toDto);
    }
}
