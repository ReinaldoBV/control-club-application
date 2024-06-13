package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Partido;
import cl.controlclub.myapp.repository.PartidoRepository;
import cl.controlclub.myapp.service.dto.PartidoDTO;
import cl.controlclub.myapp.service.mapper.PartidoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Partido}.
 */
@Service
@Transactional
public class PartidoService {

    private final Logger log = LoggerFactory.getLogger(PartidoService.class);

    private final PartidoRepository partidoRepository;

    private final PartidoMapper partidoMapper;

    public PartidoService(PartidoRepository partidoRepository, PartidoMapper partidoMapper) {
        this.partidoRepository = partidoRepository;
        this.partidoMapper = partidoMapper;
    }

    /**
     * Save a partido.
     *
     * @param partidoDTO the entity to save.
     * @return the persisted entity.
     */
    public PartidoDTO save(PartidoDTO partidoDTO) {
        log.debug("Request to save Partido : {}", partidoDTO);
        Partido partido = partidoMapper.toEntity(partidoDTO);
        partido = partidoRepository.save(partido);
        return partidoMapper.toDto(partido);
    }

    /**
     * Update a partido.
     *
     * @param partidoDTO the entity to save.
     * @return the persisted entity.
     */
    public PartidoDTO update(PartidoDTO partidoDTO) {
        log.debug("Request to update Partido : {}", partidoDTO);
        Partido partido = partidoMapper.toEntity(partidoDTO);
        partido = partidoRepository.save(partido);
        return partidoMapper.toDto(partido);
    }

    /**
     * Partially update a partido.
     *
     * @param partidoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PartidoDTO> partialUpdate(PartidoDTO partidoDTO) {
        log.debug("Request to partially update Partido : {}", partidoDTO);

        return partidoRepository
            .findById(partidoDTO.getId())
            .map(existingPartido -> {
                partidoMapper.partialUpdate(existingPartido, partidoDTO);

                return existingPartido;
            })
            .map(partidoRepository::save)
            .map(partidoMapper::toDto);
    }

    /**
     * Get all the partidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PartidoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Partidos");
        return partidoRepository.findAll(pageable).map(partidoMapper::toDto);
    }

    /**
     * Get one partido by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PartidoDTO> findOne(Long id) {
        log.debug("Request to get Partido : {}", id);
        return partidoRepository.findById(id).map(partidoMapper::toDto);
    }

    /**
     * Delete the partido by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Partido : {}", id);
        partidoRepository.deleteById(id);
    }
}
