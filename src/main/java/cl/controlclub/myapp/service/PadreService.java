package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Padre;
import cl.controlclub.myapp.repository.PadreRepository;
import cl.controlclub.myapp.service.dto.PadreDTO;
import cl.controlclub.myapp.service.mapper.PadreMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Padre}.
 */
@Service
@Transactional
public class PadreService {

    private final Logger log = LoggerFactory.getLogger(PadreService.class);

    private final PadreRepository padreRepository;

    private final PadreMapper padreMapper;

    public PadreService(PadreRepository padreRepository, PadreMapper padreMapper) {
        this.padreRepository = padreRepository;
        this.padreMapper = padreMapper;
    }

    /**
     * Save a padre.
     *
     * @param padreDTO the entity to save.
     * @return the persisted entity.
     */
    public PadreDTO save(PadreDTO padreDTO) {
        log.debug("Request to save Padre : {}", padreDTO);
        Padre padre = padreMapper.toEntity(padreDTO);
        padre = padreRepository.save(padre);
        return padreMapper.toDto(padre);
    }

    /**
     * Update a padre.
     *
     * @param padreDTO the entity to save.
     * @return the persisted entity.
     */
    public PadreDTO update(PadreDTO padreDTO) {
        log.debug("Request to update Padre : {}", padreDTO);
        Padre padre = padreMapper.toEntity(padreDTO);
        padre = padreRepository.save(padre);
        return padreMapper.toDto(padre);
    }

    /**
     * Partially update a padre.
     *
     * @param padreDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PadreDTO> partialUpdate(PadreDTO padreDTO) {
        log.debug("Request to partially update Padre : {}", padreDTO);

        return padreRepository
            .findById(padreDTO.getId())
            .map(existingPadre -> {
                padreMapper.partialUpdate(existingPadre, padreDTO);

                return existingPadre;
            })
            .map(padreRepository::save)
            .map(padreMapper::toDto);
    }

    /**
     * Get all the padres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PadreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Padres");
        return padreRepository.findAll(pageable).map(padreMapper::toDto);
    }

    /**
     * Get one padre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PadreDTO> findOne(Long id) {
        log.debug("Request to get Padre : {}", id);
        return padreRepository.findById(id).map(padreMapper::toDto);
    }

    /**
     * Delete the padre by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Padre : {}", id);
        padreRepository.deleteById(id);
    }
}
