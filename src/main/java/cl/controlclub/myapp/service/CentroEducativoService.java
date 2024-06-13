package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.CentroEducativo;
import cl.controlclub.myapp.repository.CentroEducativoRepository;
import cl.controlclub.myapp.service.dto.CentroEducativoDTO;
import cl.controlclub.myapp.service.mapper.CentroEducativoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.CentroEducativo}.
 */
@Service
@Transactional
public class CentroEducativoService {

    private final Logger log = LoggerFactory.getLogger(CentroEducativoService.class);

    private final CentroEducativoRepository centroEducativoRepository;

    private final CentroEducativoMapper centroEducativoMapper;

    public CentroEducativoService(CentroEducativoRepository centroEducativoRepository, CentroEducativoMapper centroEducativoMapper) {
        this.centroEducativoRepository = centroEducativoRepository;
        this.centroEducativoMapper = centroEducativoMapper;
    }

    /**
     * Save a centroEducativo.
     *
     * @param centroEducativoDTO the entity to save.
     * @return the persisted entity.
     */
    public CentroEducativoDTO save(CentroEducativoDTO centroEducativoDTO) {
        log.debug("Request to save CentroEducativo : {}", centroEducativoDTO);
        CentroEducativo centroEducativo = centroEducativoMapper.toEntity(centroEducativoDTO);
        centroEducativo = centroEducativoRepository.save(centroEducativo);
        return centroEducativoMapper.toDto(centroEducativo);
    }

    /**
     * Update a centroEducativo.
     *
     * @param centroEducativoDTO the entity to save.
     * @return the persisted entity.
     */
    public CentroEducativoDTO update(CentroEducativoDTO centroEducativoDTO) {
        log.debug("Request to update CentroEducativo : {}", centroEducativoDTO);
        CentroEducativo centroEducativo = centroEducativoMapper.toEntity(centroEducativoDTO);
        centroEducativo = centroEducativoRepository.save(centroEducativo);
        return centroEducativoMapper.toDto(centroEducativo);
    }

    /**
     * Partially update a centroEducativo.
     *
     * @param centroEducativoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CentroEducativoDTO> partialUpdate(CentroEducativoDTO centroEducativoDTO) {
        log.debug("Request to partially update CentroEducativo : {}", centroEducativoDTO);

        return centroEducativoRepository
            .findById(centroEducativoDTO.getId())
            .map(existingCentroEducativo -> {
                centroEducativoMapper.partialUpdate(existingCentroEducativo, centroEducativoDTO);

                return existingCentroEducativo;
            })
            .map(centroEducativoRepository::save)
            .map(centroEducativoMapper::toDto);
    }

    /**
     * Get all the centroEducativos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CentroEducativoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CentroEducativos");
        return centroEducativoRepository.findAll(pageable).map(centroEducativoMapper::toDto);
    }

    /**
     * Get one centroEducativo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CentroEducativoDTO> findOne(Long id) {
        log.debug("Request to get CentroEducativo : {}", id);
        return centroEducativoRepository.findById(id).map(centroEducativoMapper::toDto);
    }

    /**
     * Delete the centroEducativo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CentroEducativo : {}", id);
        centroEducativoRepository.deleteById(id);
    }
}
