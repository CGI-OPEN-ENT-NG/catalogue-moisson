package fr.openent.moisson.service.impl;

import fr.openent.moisson.service.OffreService;
import fr.openent.moisson.domain.Offre;
import fr.openent.moisson.repository.OffreRepository;
import fr.openent.moisson.repository.LicenceRepository;
import fr.openent.moisson.repository.search.OffreSearchRepository;
import fr.openent.moisson.service.dto.OffreDTO;
import fr.openent.moisson.service.mapper.OffreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Offre}.
 */
@Service
@Transactional
public class OffreServiceImpl implements OffreService {

    private final Logger log = LoggerFactory.getLogger(OffreServiceImpl.class);

    private final OffreRepository offreRepository;

    private final OffreMapper offreMapper;

    private final OffreSearchRepository offreSearchRepository;

    private final LicenceRepository licenceRepository;

    public OffreServiceImpl(OffreRepository offreRepository, OffreMapper offreMapper, OffreSearchRepository offreSearchRepository, LicenceRepository licenceRepository) {
        this.offreRepository = offreRepository;
        this.offreMapper = offreMapper;
        this.offreSearchRepository = offreSearchRepository;
        this.licenceRepository = licenceRepository;
    }

    @Override
    public OffreDTO save(OffreDTO offreDTO) {
        log.debug("Request to save Offre : {}", offreDTO);
        Offre offre = offreMapper.toEntity(offreDTO);
        Long licenceId = offreDTO.getLicenceId();
        licenceRepository.findById(licenceId).ifPresent(offre::licence);
        offre = offreRepository.save(offre);
        OffreDTO result = offreMapper.toDto(offre);
        offreSearchRepository.save(offre);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OffreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offres");
        return offreRepository.findAll(pageable)
            .map(offreMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OffreDTO> findOne(Long id) {
        log.debug("Request to get Offre : {}", id);
        return offreRepository.findById(id)
            .map(offreMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Offre : {}", id);
        offreRepository.deleteById(id);
        offreSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OffreDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Offres for query {}", query);
        return offreSearchRepository.search(queryStringQuery(query), pageable)
            .map(offreMapper::toDto);
    }
}
