package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Categorias;
import cl.controlclub.myapp.repository.CategoriasRepository;
import cl.controlclub.myapp.service.dto.CategoriasDTO;
import cl.controlclub.myapp.service.mapper.CategoriasMapper;
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
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Categorias}.
 */
@Service
@Transactional
public class CategoriasService {

    private final Logger log = LoggerFactory.getLogger(CategoriasService.class);

    private final CategoriasRepository categoriasRepository;

    private final CategoriasMapper categoriasMapper;

    public CategoriasService(CategoriasRepository categoriasRepository, CategoriasMapper categoriasMapper) {
        this.categoriasRepository = categoriasRepository;
        this.categoriasMapper = categoriasMapper;
    }

    /**
     * Save a categorias.
     *
     * @param categoriasDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoriasDTO save(CategoriasDTO categoriasDTO) {
        log.debug("Request to save Categorias : {}", categoriasDTO);
        Categorias categorias = categoriasMapper.toEntity(categoriasDTO);
        categorias = categoriasRepository.save(categorias);
        return categoriasMapper.toDto(categorias);
    }

    /**
     * Update a categorias.
     *
     * @param categoriasDTO the entity to save.
     * @return the persisted entity.
     */
    public CategoriasDTO update(CategoriasDTO categoriasDTO) {
        log.debug("Request to update Categorias : {}", categoriasDTO);
        Categorias categorias = categoriasMapper.toEntity(categoriasDTO);
        categorias = categoriasRepository.save(categorias);
        return categoriasMapper.toDto(categorias);
    }

    /**
     * Partially update a categorias.
     *
     * @param categoriasDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CategoriasDTO> partialUpdate(CategoriasDTO categoriasDTO) {
        log.debug("Request to partially update Categorias : {}", categoriasDTO);

        return categoriasRepository
            .findById(categoriasDTO.getId())
            .map(existingCategorias -> {
                categoriasMapper.partialUpdate(existingCategorias, categoriasDTO);

                return existingCategorias;
            })
            .map(categoriasRepository::save)
            .map(categoriasMapper::toDto);
    }

    /**
     * Get all the categorias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categorias");
        return categoriasRepository.findAll(pageable).map(categoriasMapper::toDto);
    }

    /**
     *  Get all the categorias where Jugador is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriasDTO> findAllWhereJugadorIsNull() {
        log.debug("Request to get all categorias where Jugador is null");
        return StreamSupport.stream(categoriasRepository.findAll().spliterator(), false)
            .filter(categorias -> categorias.getJugador() == null)
            .map(categoriasMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one categorias by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CategoriasDTO> findOne(Long id) {
        log.debug("Request to get Categorias : {}", id);
        return categoriasRepository.findById(id).map(categoriasMapper::toDto);
    }

    /**
     * Delete the categorias by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Categorias : {}", id);
        categoriasRepository.deleteById(id);
    }
}
