package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.EstadisticasBaloncesto;
import cl.controlclub.myapp.repository.EstadisticasBaloncestoRepository;
import cl.controlclub.myapp.service.dto.EstadisticasBaloncestoDTO;
import cl.controlclub.myapp.service.mapper.EstadisticasBaloncestoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.EstadisticasBaloncesto}.
 */
@Service
@Transactional
public class EstadisticasBaloncestoService {

    private final Logger log = LoggerFactory.getLogger(EstadisticasBaloncestoService.class);

    private final EstadisticasBaloncestoRepository estadisticasBaloncestoRepository;

    private final EstadisticasBaloncestoMapper estadisticasBaloncestoMapper;

    public EstadisticasBaloncestoService(
        EstadisticasBaloncestoRepository estadisticasBaloncestoRepository,
        EstadisticasBaloncestoMapper estadisticasBaloncestoMapper
    ) {
        this.estadisticasBaloncestoRepository = estadisticasBaloncestoRepository;
        this.estadisticasBaloncestoMapper = estadisticasBaloncestoMapper;
    }

    /**
     * Save a estadisticasBaloncesto.
     *
     * @param estadisticasBaloncestoDTO the entity to save.
     * @return the persisted entity.
     */
    public EstadisticasBaloncestoDTO save(EstadisticasBaloncestoDTO estadisticasBaloncestoDTO) {
        log.debug("Request to save EstadisticasBaloncesto : {}", estadisticasBaloncestoDTO);
        EstadisticasBaloncesto estadisticasBaloncesto = estadisticasBaloncestoMapper.toEntity(estadisticasBaloncestoDTO);
        estadisticasBaloncesto = estadisticasBaloncestoRepository.save(estadisticasBaloncesto);
        return estadisticasBaloncestoMapper.toDto(estadisticasBaloncesto);
    }

    /**
     * Update a estadisticasBaloncesto.
     *
     * @param estadisticasBaloncestoDTO the entity to save.
     * @return the persisted entity.
     */
    public EstadisticasBaloncestoDTO update(EstadisticasBaloncestoDTO estadisticasBaloncestoDTO) {
        log.debug("Request to update EstadisticasBaloncesto : {}", estadisticasBaloncestoDTO);
        EstadisticasBaloncesto estadisticasBaloncesto = estadisticasBaloncestoMapper.toEntity(estadisticasBaloncestoDTO);
        estadisticasBaloncesto = estadisticasBaloncestoRepository.save(estadisticasBaloncesto);
        return estadisticasBaloncestoMapper.toDto(estadisticasBaloncesto);
    }

    /**
     * Partially update a estadisticasBaloncesto.
     *
     * @param estadisticasBaloncestoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EstadisticasBaloncestoDTO> partialUpdate(EstadisticasBaloncestoDTO estadisticasBaloncestoDTO) {
        log.debug("Request to partially update EstadisticasBaloncesto : {}", estadisticasBaloncestoDTO);

        return estadisticasBaloncestoRepository
            .findById(estadisticasBaloncestoDTO.getId())
            .map(existingEstadisticasBaloncesto -> {
                estadisticasBaloncestoMapper.partialUpdate(existingEstadisticasBaloncesto, estadisticasBaloncestoDTO);

                return existingEstadisticasBaloncesto;
            })
            .map(estadisticasBaloncestoRepository::save)
            .map(estadisticasBaloncestoMapper::toDto);
    }

    /**
     * Get all the estadisticasBaloncestos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EstadisticasBaloncestoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstadisticasBaloncestos");
        return estadisticasBaloncestoRepository.findAll(pageable).map(estadisticasBaloncestoMapper::toDto);
    }

    /**
     * Get one estadisticasBaloncesto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EstadisticasBaloncestoDTO> findOne(Long id) {
        log.debug("Request to get EstadisticasBaloncesto : {}", id);
        return estadisticasBaloncestoRepository.findById(id).map(estadisticasBaloncestoMapper::toDto);
    }

    /**
     * Delete the estadisticasBaloncesto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EstadisticasBaloncesto : {}", id);
        estadisticasBaloncestoRepository.deleteById(id);
    }
}
