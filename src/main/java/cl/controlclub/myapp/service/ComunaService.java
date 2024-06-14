package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Comuna;
import cl.controlclub.myapp.repository.ComunaRepository;
import cl.controlclub.myapp.service.dto.ComunaDTO;
import cl.controlclub.myapp.service.mapper.ComunaMapper;
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
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Comuna}.
 */
@Service
@Transactional
public class ComunaService {

    private final Logger log = LoggerFactory.getLogger(ComunaService.class);

    private final ComunaRepository comunaRepository;

    private final ComunaMapper comunaMapper;

    public ComunaService(ComunaRepository comunaRepository, ComunaMapper comunaMapper) {
        this.comunaRepository = comunaRepository;
        this.comunaMapper = comunaMapper;
    }

    /**
     * Save a comuna.
     *
     * @param comunaDTO the entity to save.
     * @return the persisted entity.
     */
    public ComunaDTO save(ComunaDTO comunaDTO) {
        log.debug("Request to save Comuna : {}", comunaDTO);
        Comuna comuna = comunaMapper.toEntity(comunaDTO);
        comuna = comunaRepository.save(comuna);
        return comunaMapper.toDto(comuna);
    }

    /**
     * Update a comuna.
     *
     * @param comunaDTO the entity to save.
     * @return the persisted entity.
     */
    public ComunaDTO update(ComunaDTO comunaDTO) {
        log.debug("Request to update Comuna : {}", comunaDTO);
        Comuna comuna = comunaMapper.toEntity(comunaDTO);
        comuna = comunaRepository.save(comuna);
        return comunaMapper.toDto(comuna);
    }

    /**
     * Partially update a comuna.
     *
     * @param comunaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ComunaDTO> partialUpdate(ComunaDTO comunaDTO) {
        log.debug("Request to partially update Comuna : {}", comunaDTO);

        return comunaRepository
            .findById(comunaDTO.getId())
            .map(existingComuna -> {
                comunaMapper.partialUpdate(existingComuna, comunaDTO);

                return existingComuna;
            })
            .map(comunaRepository::save)
            .map(comunaMapper::toDto);
    }

    /**
     * Get all the comunas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComunaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Comunas");
        return comunaRepository.findAll(pageable).map(comunaMapper::toDto);
    }

    /**
     *  Get all the comunas where Jugador is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ComunaDTO> findAllWhereJugadorIsNull() {
        log.debug("Request to get all comunas where Jugador is null");
        return StreamSupport.stream(comunaRepository.findAll().spliterator(), false)
            .filter(comuna -> comuna.getJugador() == null)
            .map(comunaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one comuna by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComunaDTO> findOne(Long id) {
        log.debug("Request to get Comuna : {}", id);
        return comunaRepository.findById(id).map(comunaMapper::toDto);
    }

    /**
     * Delete the comuna by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Comuna : {}", id);
        comunaRepository.deleteById(id);
    }
}
