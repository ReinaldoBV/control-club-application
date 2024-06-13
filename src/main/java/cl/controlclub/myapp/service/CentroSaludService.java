package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.CentroSalud;
import cl.controlclub.myapp.repository.CentroSaludRepository;
import cl.controlclub.myapp.service.dto.CentroSaludDTO;
import cl.controlclub.myapp.service.mapper.CentroSaludMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.CentroSalud}.
 */
@Service
@Transactional
public class CentroSaludService {

    private final Logger log = LoggerFactory.getLogger(CentroSaludService.class);

    private final CentroSaludRepository centroSaludRepository;

    private final CentroSaludMapper centroSaludMapper;

    public CentroSaludService(CentroSaludRepository centroSaludRepository, CentroSaludMapper centroSaludMapper) {
        this.centroSaludRepository = centroSaludRepository;
        this.centroSaludMapper = centroSaludMapper;
    }

    /**
     * Save a centroSalud.
     *
     * @param centroSaludDTO the entity to save.
     * @return the persisted entity.
     */
    public CentroSaludDTO save(CentroSaludDTO centroSaludDTO) {
        log.debug("Request to save CentroSalud : {}", centroSaludDTO);
        CentroSalud centroSalud = centroSaludMapper.toEntity(centroSaludDTO);
        centroSalud = centroSaludRepository.save(centroSalud);
        return centroSaludMapper.toDto(centroSalud);
    }

    /**
     * Update a centroSalud.
     *
     * @param centroSaludDTO the entity to save.
     * @return the persisted entity.
     */
    public CentroSaludDTO update(CentroSaludDTO centroSaludDTO) {
        log.debug("Request to update CentroSalud : {}", centroSaludDTO);
        CentroSalud centroSalud = centroSaludMapper.toEntity(centroSaludDTO);
        centroSalud = centroSaludRepository.save(centroSalud);
        return centroSaludMapper.toDto(centroSalud);
    }

    /**
     * Partially update a centroSalud.
     *
     * @param centroSaludDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CentroSaludDTO> partialUpdate(CentroSaludDTO centroSaludDTO) {
        log.debug("Request to partially update CentroSalud : {}", centroSaludDTO);

        return centroSaludRepository
            .findById(centroSaludDTO.getId())
            .map(existingCentroSalud -> {
                centroSaludMapper.partialUpdate(existingCentroSalud, centroSaludDTO);

                return existingCentroSalud;
            })
            .map(centroSaludRepository::save)
            .map(centroSaludMapper::toDto);
    }

    /**
     * Get all the centroSaluds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CentroSaludDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CentroSaluds");
        return centroSaludRepository.findAll(pageable).map(centroSaludMapper::toDto);
    }

    /**
     *  Get all the centroSaluds where Jugador is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CentroSaludDTO> findAllWhereJugadorIsNull() {
        log.debug("Request to get all centroSaluds where Jugador is null");
        return StreamSupport.stream(centroSaludRepository.findAll().spliterator(), false)
            .filter(centroSalud -> centroSalud.getJugador() == null)
            .map(centroSaludMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one centroSalud by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CentroSaludDTO> findOne(Long id) {
        log.debug("Request to get CentroSalud : {}", id);
        return centroSaludRepository.findById(id).map(centroSaludMapper::toDto);
    }

    /**
     * Delete the centroSalud by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CentroSalud : {}", id);
        centroSaludRepository.deleteById(id);
    }
}
