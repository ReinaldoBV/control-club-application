package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.TorneosParticipaciones;
import cl.controlclub.myapp.repository.TorneosParticipacionesRepository;
import cl.controlclub.myapp.service.dto.TorneosParticipacionesDTO;
import cl.controlclub.myapp.service.mapper.TorneosParticipacionesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.TorneosParticipaciones}.
 */
@Service
@Transactional
public class TorneosParticipacionesService {

    private final Logger log = LoggerFactory.getLogger(TorneosParticipacionesService.class);

    private final TorneosParticipacionesRepository torneosParticipacionesRepository;

    private final TorneosParticipacionesMapper torneosParticipacionesMapper;

    public TorneosParticipacionesService(
        TorneosParticipacionesRepository torneosParticipacionesRepository,
        TorneosParticipacionesMapper torneosParticipacionesMapper
    ) {
        this.torneosParticipacionesRepository = torneosParticipacionesRepository;
        this.torneosParticipacionesMapper = torneosParticipacionesMapper;
    }

    /**
     * Save a torneosParticipaciones.
     *
     * @param torneosParticipacionesDTO the entity to save.
     * @return the persisted entity.
     */
    public TorneosParticipacionesDTO save(TorneosParticipacionesDTO torneosParticipacionesDTO) {
        log.debug("Request to save TorneosParticipaciones : {}", torneosParticipacionesDTO);
        TorneosParticipaciones torneosParticipaciones = torneosParticipacionesMapper.toEntity(torneosParticipacionesDTO);
        torneosParticipaciones = torneosParticipacionesRepository.save(torneosParticipaciones);
        return torneosParticipacionesMapper.toDto(torneosParticipaciones);
    }

    /**
     * Update a torneosParticipaciones.
     *
     * @param torneosParticipacionesDTO the entity to save.
     * @return the persisted entity.
     */
    public TorneosParticipacionesDTO update(TorneosParticipacionesDTO torneosParticipacionesDTO) {
        log.debug("Request to update TorneosParticipaciones : {}", torneosParticipacionesDTO);
        TorneosParticipaciones torneosParticipaciones = torneosParticipacionesMapper.toEntity(torneosParticipacionesDTO);
        torneosParticipaciones = torneosParticipacionesRepository.save(torneosParticipaciones);
        return torneosParticipacionesMapper.toDto(torneosParticipaciones);
    }

    /**
     * Partially update a torneosParticipaciones.
     *
     * @param torneosParticipacionesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TorneosParticipacionesDTO> partialUpdate(TorneosParticipacionesDTO torneosParticipacionesDTO) {
        log.debug("Request to partially update TorneosParticipaciones : {}", torneosParticipacionesDTO);

        return torneosParticipacionesRepository
            .findById(torneosParticipacionesDTO.getId())
            .map(existingTorneosParticipaciones -> {
                torneosParticipacionesMapper.partialUpdate(existingTorneosParticipaciones, torneosParticipacionesDTO);

                return existingTorneosParticipaciones;
            })
            .map(torneosParticipacionesRepository::save)
            .map(torneosParticipacionesMapper::toDto);
    }

    /**
     * Get all the torneosParticipaciones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TorneosParticipacionesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TorneosParticipaciones");
        return torneosParticipacionesRepository.findAll(pageable).map(torneosParticipacionesMapper::toDto);
    }

    /**
     * Get one torneosParticipaciones by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TorneosParticipacionesDTO> findOne(Long id) {
        log.debug("Request to get TorneosParticipaciones : {}", id);
        return torneosParticipacionesRepository.findById(id).map(torneosParticipacionesMapper::toDto);
    }

    /**
     * Delete the torneosParticipaciones by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TorneosParticipaciones : {}", id);
        torneosParticipacionesRepository.deleteById(id);
    }
}
