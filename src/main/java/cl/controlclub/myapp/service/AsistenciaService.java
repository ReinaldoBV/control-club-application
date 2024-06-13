package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Asistencia;
import cl.controlclub.myapp.repository.AsistenciaRepository;
import cl.controlclub.myapp.service.dto.AsistenciaDTO;
import cl.controlclub.myapp.service.mapper.AsistenciaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Asistencia}.
 */
@Service
@Transactional
public class AsistenciaService {

    private final Logger log = LoggerFactory.getLogger(AsistenciaService.class);

    private final AsistenciaRepository asistenciaRepository;

    private final AsistenciaMapper asistenciaMapper;

    public AsistenciaService(AsistenciaRepository asistenciaRepository, AsistenciaMapper asistenciaMapper) {
        this.asistenciaRepository = asistenciaRepository;
        this.asistenciaMapper = asistenciaMapper;
    }

    /**
     * Save a asistencia.
     *
     * @param asistenciaDTO the entity to save.
     * @return the persisted entity.
     */
    public AsistenciaDTO save(AsistenciaDTO asistenciaDTO) {
        log.debug("Request to save Asistencia : {}", asistenciaDTO);
        Asistencia asistencia = asistenciaMapper.toEntity(asistenciaDTO);
        asistencia = asistenciaRepository.save(asistencia);
        return asistenciaMapper.toDto(asistencia);
    }

    /**
     * Update a asistencia.
     *
     * @param asistenciaDTO the entity to save.
     * @return the persisted entity.
     */
    public AsistenciaDTO update(AsistenciaDTO asistenciaDTO) {
        log.debug("Request to update Asistencia : {}", asistenciaDTO);
        Asistencia asistencia = asistenciaMapper.toEntity(asistenciaDTO);
        asistencia = asistenciaRepository.save(asistencia);
        return asistenciaMapper.toDto(asistencia);
    }

    /**
     * Partially update a asistencia.
     *
     * @param asistenciaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AsistenciaDTO> partialUpdate(AsistenciaDTO asistenciaDTO) {
        log.debug("Request to partially update Asistencia : {}", asistenciaDTO);

        return asistenciaRepository
            .findById(asistenciaDTO.getId())
            .map(existingAsistencia -> {
                asistenciaMapper.partialUpdate(existingAsistencia, asistenciaDTO);

                return existingAsistencia;
            })
            .map(asistenciaRepository::save)
            .map(asistenciaMapper::toDto);
    }

    /**
     * Get all the asistencias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AsistenciaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Asistencias");
        return asistenciaRepository.findAll(pageable).map(asistenciaMapper::toDto);
    }

    /**
     * Get one asistencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AsistenciaDTO> findOne(Long id) {
        log.debug("Request to get Asistencia : {}", id);
        return asistenciaRepository.findById(id).map(asistenciaMapper::toDto);
    }

    /**
     * Delete the asistencia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Asistencia : {}", id);
        asistenciaRepository.deleteById(id);
    }
}
