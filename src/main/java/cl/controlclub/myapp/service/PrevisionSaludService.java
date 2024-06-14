package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.PrevisionSalud;
import cl.controlclub.myapp.repository.PrevisionSaludRepository;
import cl.controlclub.myapp.service.dto.PrevisionSaludDTO;
import cl.controlclub.myapp.service.mapper.PrevisionSaludMapper;
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
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.PrevisionSalud}.
 */
@Service
@Transactional
public class PrevisionSaludService {

    private final Logger log = LoggerFactory.getLogger(PrevisionSaludService.class);

    private final PrevisionSaludRepository previsionSaludRepository;

    private final PrevisionSaludMapper previsionSaludMapper;

    public PrevisionSaludService(PrevisionSaludRepository previsionSaludRepository, PrevisionSaludMapper previsionSaludMapper) {
        this.previsionSaludRepository = previsionSaludRepository;
        this.previsionSaludMapper = previsionSaludMapper;
    }

    /**
     * Save a previsionSalud.
     *
     * @param previsionSaludDTO the entity to save.
     * @return the persisted entity.
     */
    public PrevisionSaludDTO save(PrevisionSaludDTO previsionSaludDTO) {
        log.debug("Request to save PrevisionSalud : {}", previsionSaludDTO);
        PrevisionSalud previsionSalud = previsionSaludMapper.toEntity(previsionSaludDTO);
        previsionSalud = previsionSaludRepository.save(previsionSalud);
        return previsionSaludMapper.toDto(previsionSalud);
    }

    /**
     * Update a previsionSalud.
     *
     * @param previsionSaludDTO the entity to save.
     * @return the persisted entity.
     */
    public PrevisionSaludDTO update(PrevisionSaludDTO previsionSaludDTO) {
        log.debug("Request to update PrevisionSalud : {}", previsionSaludDTO);
        PrevisionSalud previsionSalud = previsionSaludMapper.toEntity(previsionSaludDTO);
        previsionSalud = previsionSaludRepository.save(previsionSalud);
        return previsionSaludMapper.toDto(previsionSalud);
    }

    /**
     * Partially update a previsionSalud.
     *
     * @param previsionSaludDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PrevisionSaludDTO> partialUpdate(PrevisionSaludDTO previsionSaludDTO) {
        log.debug("Request to partially update PrevisionSalud : {}", previsionSaludDTO);

        return previsionSaludRepository
            .findById(previsionSaludDTO.getId())
            .map(existingPrevisionSalud -> {
                previsionSaludMapper.partialUpdate(existingPrevisionSalud, previsionSaludDTO);

                return existingPrevisionSalud;
            })
            .map(previsionSaludRepository::save)
            .map(previsionSaludMapper::toDto);
    }

    /**
     * Get all the previsionSaluds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrevisionSaludDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PrevisionSaluds");
        return previsionSaludRepository.findAll(pageable).map(previsionSaludMapper::toDto);
    }

    /**
     *  Get all the previsionSaluds where Jugador is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PrevisionSaludDTO> findAllWhereJugadorIsNull() {
        log.debug("Request to get all previsionSaluds where Jugador is null");
        return StreamSupport.stream(previsionSaludRepository.findAll().spliterator(), false)
            .filter(previsionSalud -> previsionSalud.getJugador() == null)
            .map(previsionSaludMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one previsionSalud by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrevisionSaludDTO> findOne(Long id) {
        log.debug("Request to get PrevisionSalud : {}", id);
        return previsionSaludRepository.findById(id).map(previsionSaludMapper::toDto);
    }

    /**
     * Delete the previsionSalud by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrevisionSalud : {}", id);
        previsionSaludRepository.deleteById(id);
    }
}
