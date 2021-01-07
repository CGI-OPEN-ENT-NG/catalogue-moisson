package fr.openent.moisson.service.impl;

import fr.openent.moisson.domain.Licence;
import fr.openent.moisson.repository.LicenceRepository;
import fr.openent.moisson.repository.search.LicenceSearchRepository;
import fr.openent.moisson.service.LicenceService;
import fr.openent.moisson.service.dto.LicenceDTO;
import fr.openent.moisson.service.mapper.LicenceMapper;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Licence}.
 */
@Service
@Transactional
public class LicenceServiceImpl implements LicenceService {

    private final Logger log = LoggerFactory.getLogger(LicenceServiceImpl.class);

    private final LicenceRepository licenceRepository;

    private final LicenceMapper licenceMapper;

    private final LicenceSearchRepository licenceSearchRepository;

    public LicenceServiceImpl(LicenceRepository licenceRepository, LicenceMapper licenceMapper, LicenceSearchRepository licenceSearchRepository) {
        this.licenceRepository = licenceRepository;
        this.licenceMapper = licenceMapper;
        this.licenceSearchRepository = licenceSearchRepository;
    }

    @Override
    public LicenceDTO save(LicenceDTO licenceDTO) {
        log.debug("Request to save Licence : {}", licenceDTO);
        Licence licence = licenceMapper.toEntity(licenceDTO);
        licence = licenceRepository.save(licence);
        LicenceDTO result = licenceMapper.toDto(licence);
        licenceSearchRepository.save(licence);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LicenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Licences");
        return licenceRepository.findAll(pageable)
            .map(licenceMapper::toDto);
    }



    /**
     *  Get all the licences where Offre is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LicenceDTO> findAllWhereOffreIsNull() {
        log.debug("Request to get all licences where Offre is null");
        return StreamSupport
            .stream(licenceRepository.findAll().spliterator(), false)
            .filter(licence -> licence.getOffre() == null)
            .map(licenceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the licences where Lep is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LicenceDTO> findAllWhereLepIsNull() {
        log.debug("Request to get all licences where Lep is null");
        return StreamSupport
            .stream(licenceRepository.findAll().spliterator(), false)
            .filter(licence -> licence.getLep() == null)
            .map(licenceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LicenceDTO> findOne(Long id) {
        log.debug("Request to get Licence : {}", id);
        return licenceRepository.findById(id)
            .map(licenceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Licence : {}", id);
        licenceRepository.deleteById(id);
        licenceSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LicenceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Licences for query {}", query);
        return licenceSearchRepository.search(queryStringQuery(query), pageable)
            .map(licenceMapper::toDto);
    }
}
