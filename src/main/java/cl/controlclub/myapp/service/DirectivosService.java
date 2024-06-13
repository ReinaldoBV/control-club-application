package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Directivos;
import cl.controlclub.myapp.repository.DirectivosRepository;
import cl.controlclub.myapp.service.dto.DirectivosDTO;
import cl.controlclub.myapp.service.mapper.DirectivosMapper;
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
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Directivos}.
 */
@Service
@Transactional
public class DirectivosService {

    private final Logger log = LoggerFactory.getLogger(DirectivosService.class);

    private final DirectivosRepository directivosRepository;

    private final DirectivosMapper directivosMapper;

    public DirectivosService(DirectivosRepository directivosRepository, DirectivosMapper directivosMapper) {
        this.directivosRepository = directivosRepository;
        this.directivosMapper = directivosMapper;
    }

    /**
     * Save a directivos.
     *
     * @param directivosDTO the entity to save.
     * @return the persisted entity.
     */
    public DirectivosDTO save(DirectivosDTO directivosDTO) {
        log.debug("Request to save Directivos : {}", directivosDTO);
        Directivos directivos = directivosMapper.toEntity(directivosDTO);
        directivos = directivosRepository.save(directivos);
        return directivosMapper.toDto(directivos);
    }

    /**
     * Update a directivos.
     *
     * @param directivosDTO the entity to save.
     * @return the persisted entity.
     */
    public DirectivosDTO update(DirectivosDTO directivosDTO) {
        log.debug("Request to update Directivos : {}", directivosDTO);
        Directivos directivos = directivosMapper.toEntity(directivosDTO);
        directivos = directivosRepository.save(directivos);
        return directivosMapper.toDto(directivos);
    }

    /**
     * Partially update a directivos.
     *
     * @param directivosDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DirectivosDTO> partialUpdate(DirectivosDTO directivosDTO) {
        log.debug("Request to partially update Directivos : {}", directivosDTO);

        return directivosRepository
            .findById(directivosDTO.getId())
            .map(existingDirectivos -> {
                directivosMapper.partialUpdate(existingDirectivos, directivosDTO);

                return existingDirectivos;
            })
            .map(directivosRepository::save)
            .map(directivosMapper::toDto);
    }

    /**
     * Get all the directivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DirectivosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Directivos");
        return directivosRepository.findAll(pageable).map(directivosMapper::toDto);
    }

    /**
     *  Get all the directivos where Usuario is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DirectivosDTO> findAllWhereUsuarioIsNull() {
        log.debug("Request to get all directivos where Usuario is null");
        return StreamSupport.stream(directivosRepository.findAll().spliterator(), false)
            .filter(directivos -> directivos.getUsuario() == null)
            .map(directivosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one directivos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DirectivosDTO> findOne(Long id) {
        log.debug("Request to get Directivos : {}", id);
        return directivosRepository.findById(id).map(directivosMapper::toDto);
    }

    /**
     * Delete the directivos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Directivos : {}", id);
        directivosRepository.deleteById(id);
    }
}
