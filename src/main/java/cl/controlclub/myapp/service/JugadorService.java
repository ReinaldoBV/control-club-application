package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.repository.JugadorRepository;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import cl.controlclub.myapp.service.mapper.JugadorMapper;
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
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Jugador}.
 */
@Service
@Transactional
public class JugadorService {

    private final Logger log = LoggerFactory.getLogger(JugadorService.class);

    private final JugadorRepository jugadorRepository;

    private final JugadorMapper jugadorMapper;

    public JugadorService(JugadorRepository jugadorRepository, JugadorMapper jugadorMapper) {
        this.jugadorRepository = jugadorRepository;
        this.jugadorMapper = jugadorMapper;
    }

    /**
     * Save a jugador.
     *
     * @param jugadorDTO the entity to save.
     * @return the persisted entity.
     */
    public JugadorDTO save(JugadorDTO jugadorDTO) {
        log.debug("Request to save Jugador : {}", jugadorDTO);
        Jugador jugador = jugadorMapper.toEntity(jugadorDTO);
        jugador = jugadorRepository.save(jugador);
        return jugadorMapper.toDto(jugador);
    }

    /**
     * Update a jugador.
     *
     * @param jugadorDTO the entity to save.
     * @return the persisted entity.
     */
    public JugadorDTO update(JugadorDTO jugadorDTO) {
        log.debug("Request to update Jugador : {}", jugadorDTO);
        Jugador jugador = jugadorMapper.toEntity(jugadorDTO);
        jugador = jugadorRepository.save(jugador);
        return jugadorMapper.toDto(jugador);
    }

    /**
     * Partially update a jugador.
     *
     * @param jugadorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JugadorDTO> partialUpdate(JugadorDTO jugadorDTO) {
        log.debug("Request to partially update Jugador : {}", jugadorDTO);

        return jugadorRepository
            .findById(jugadorDTO.getId())
            .map(existingJugador -> {
                jugadorMapper.partialUpdate(existingJugador, jugadorDTO);

                return existingJugador;
            })
            .map(jugadorRepository::save)
            .map(jugadorMapper::toDto);
    }

    /**
     * Get all the jugadors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JugadorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Jugadors");
        return jugadorRepository.findAll(pageable).map(jugadorMapper::toDto);
    }

    /**
     *  Get all the jugadors where Usuario is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<JugadorDTO> findAllWhereUsuarioIsNull() {
        log.debug("Request to get all jugadors where Usuario is null");
        return StreamSupport.stream(jugadorRepository.findAll().spliterator(), false)
            .filter(jugador -> jugador.getUsuario() == null)
            .map(jugadorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one jugador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JugadorDTO> findOne(Long id) {
        log.debug("Request to get Jugador : {}", id);
        return jugadorRepository.findById(id).map(jugadorMapper::toDto);
    }

    /**
     * Delete the jugador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Jugador : {}", id);
        jugadorRepository.deleteById(id);
    }
}
