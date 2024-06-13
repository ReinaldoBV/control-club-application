package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Mensaje;
import cl.controlclub.myapp.repository.MensajeRepository;
import cl.controlclub.myapp.service.dto.MensajeDTO;
import cl.controlclub.myapp.service.mapper.MensajeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Mensaje}.
 */
@Service
@Transactional
public class MensajeService {

    private final Logger log = LoggerFactory.getLogger(MensajeService.class);

    private final MensajeRepository mensajeRepository;

    private final MensajeMapper mensajeMapper;

    public MensajeService(MensajeRepository mensajeRepository, MensajeMapper mensajeMapper) {
        this.mensajeRepository = mensajeRepository;
        this.mensajeMapper = mensajeMapper;
    }

    /**
     * Save a mensaje.
     *
     * @param mensajeDTO the entity to save.
     * @return the persisted entity.
     */
    public MensajeDTO save(MensajeDTO mensajeDTO) {
        log.debug("Request to save Mensaje : {}", mensajeDTO);
        Mensaje mensaje = mensajeMapper.toEntity(mensajeDTO);
        mensaje = mensajeRepository.save(mensaje);
        return mensajeMapper.toDto(mensaje);
    }

    /**
     * Update a mensaje.
     *
     * @param mensajeDTO the entity to save.
     * @return the persisted entity.
     */
    public MensajeDTO update(MensajeDTO mensajeDTO) {
        log.debug("Request to update Mensaje : {}", mensajeDTO);
        Mensaje mensaje = mensajeMapper.toEntity(mensajeDTO);
        mensaje = mensajeRepository.save(mensaje);
        return mensajeMapper.toDto(mensaje);
    }

    /**
     * Partially update a mensaje.
     *
     * @param mensajeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MensajeDTO> partialUpdate(MensajeDTO mensajeDTO) {
        log.debug("Request to partially update Mensaje : {}", mensajeDTO);

        return mensajeRepository
            .findById(mensajeDTO.getId())
            .map(existingMensaje -> {
                mensajeMapper.partialUpdate(existingMensaje, mensajeDTO);

                return existingMensaje;
            })
            .map(mensajeRepository::save)
            .map(mensajeMapper::toDto);
    }

    /**
     * Get all the mensajes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MensajeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mensajes");
        return mensajeRepository.findAll(pageable).map(mensajeMapper::toDto);
    }

    /**
     * Get one mensaje by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MensajeDTO> findOne(Long id) {
        log.debug("Request to get Mensaje : {}", id);
        return mensajeRepository.findById(id).map(mensajeMapper::toDto);
    }

    /**
     * Delete the mensaje by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Mensaje : {}", id);
        mensajeRepository.deleteById(id);
    }
}
