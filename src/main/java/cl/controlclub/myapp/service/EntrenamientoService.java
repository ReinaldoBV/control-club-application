package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Entrenamiento;
import cl.controlclub.myapp.repository.EntrenamientoRepository;
import cl.controlclub.myapp.service.dto.EntrenamientoDTO;
import cl.controlclub.myapp.service.mapper.EntrenamientoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Entrenamiento}.
 */
@Service
@Transactional
public class EntrenamientoService {

    private final Logger log = LoggerFactory.getLogger(EntrenamientoService.class);

    private final EntrenamientoRepository entrenamientoRepository;

    private final EntrenamientoMapper entrenamientoMapper;

    public EntrenamientoService(EntrenamientoRepository entrenamientoRepository, EntrenamientoMapper entrenamientoMapper) {
        this.entrenamientoRepository = entrenamientoRepository;
        this.entrenamientoMapper = entrenamientoMapper;
    }

    /**
     * Save a entrenamiento.
     *
     * @param entrenamientoDTO the entity to save.
     * @return the persisted entity.
     */
    public EntrenamientoDTO save(EntrenamientoDTO entrenamientoDTO) {
        log.debug("Request to save Entrenamiento : {}", entrenamientoDTO);
        Entrenamiento entrenamiento = entrenamientoMapper.toEntity(entrenamientoDTO);
        entrenamiento = entrenamientoRepository.save(entrenamiento);
        return entrenamientoMapper.toDto(entrenamiento);
    }

    /**
     * Update a entrenamiento.
     *
     * @param entrenamientoDTO the entity to save.
     * @return the persisted entity.
     */
    public EntrenamientoDTO update(EntrenamientoDTO entrenamientoDTO) {
        log.debug("Request to update Entrenamiento : {}", entrenamientoDTO);
        Entrenamiento entrenamiento = entrenamientoMapper.toEntity(entrenamientoDTO);
        entrenamiento = entrenamientoRepository.save(entrenamiento);
        return entrenamientoMapper.toDto(entrenamiento);
    }

    /**
     * Partially update a entrenamiento.
     *
     * @param entrenamientoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EntrenamientoDTO> partialUpdate(EntrenamientoDTO entrenamientoDTO) {
        log.debug("Request to partially update Entrenamiento : {}", entrenamientoDTO);

        return entrenamientoRepository
            .findById(entrenamientoDTO.getId())
            .map(existingEntrenamiento -> {
                entrenamientoMapper.partialUpdate(existingEntrenamiento, entrenamientoDTO);

                return existingEntrenamiento;
            })
            .map(entrenamientoRepository::save)
            .map(entrenamientoMapper::toDto);
    }

    /**
     * Get all the entrenamientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EntrenamientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entrenamientos");
        return entrenamientoRepository.findAll(pageable).map(entrenamientoMapper::toDto);
    }

    /**
     * Get one entrenamiento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntrenamientoDTO> findOne(Long id) {
        log.debug("Request to get Entrenamiento : {}", id);
        return entrenamientoRepository.findById(id).map(entrenamientoMapper::toDto);
    }

    /**
     * Delete the entrenamiento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Entrenamiento : {}", id);
        entrenamientoRepository.deleteById(id);
    }
}
