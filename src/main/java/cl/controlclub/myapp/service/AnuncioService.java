package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Anuncio;
import cl.controlclub.myapp.repository.AnuncioRepository;
import cl.controlclub.myapp.service.dto.AnuncioDTO;
import cl.controlclub.myapp.service.mapper.AnuncioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Anuncio}.
 */
@Service
@Transactional
public class AnuncioService {

    private final Logger log = LoggerFactory.getLogger(AnuncioService.class);

    private final AnuncioRepository anuncioRepository;

    private final AnuncioMapper anuncioMapper;

    public AnuncioService(AnuncioRepository anuncioRepository, AnuncioMapper anuncioMapper) {
        this.anuncioRepository = anuncioRepository;
        this.anuncioMapper = anuncioMapper;
    }

    /**
     * Save a anuncio.
     *
     * @param anuncioDTO the entity to save.
     * @return the persisted entity.
     */
    public AnuncioDTO save(AnuncioDTO anuncioDTO) {
        log.debug("Request to save Anuncio : {}", anuncioDTO);
        Anuncio anuncio = anuncioMapper.toEntity(anuncioDTO);
        anuncio = anuncioRepository.save(anuncio);
        return anuncioMapper.toDto(anuncio);
    }

    /**
     * Update a anuncio.
     *
     * @param anuncioDTO the entity to save.
     * @return the persisted entity.
     */
    public AnuncioDTO update(AnuncioDTO anuncioDTO) {
        log.debug("Request to update Anuncio : {}", anuncioDTO);
        Anuncio anuncio = anuncioMapper.toEntity(anuncioDTO);
        anuncio = anuncioRepository.save(anuncio);
        return anuncioMapper.toDto(anuncio);
    }

    /**
     * Partially update a anuncio.
     *
     * @param anuncioDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnuncioDTO> partialUpdate(AnuncioDTO anuncioDTO) {
        log.debug("Request to partially update Anuncio : {}", anuncioDTO);

        return anuncioRepository
            .findById(anuncioDTO.getId())
            .map(existingAnuncio -> {
                anuncioMapper.partialUpdate(existingAnuncio, anuncioDTO);

                return existingAnuncio;
            })
            .map(anuncioRepository::save)
            .map(anuncioMapper::toDto);
    }

    /**
     * Get all the anuncios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnuncioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Anuncios");
        return anuncioRepository.findAll(pageable).map(anuncioMapper::toDto);
    }

    /**
     * Get one anuncio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnuncioDTO> findOne(Long id) {
        log.debug("Request to get Anuncio : {}", id);
        return anuncioRepository.findById(id).map(anuncioMapper::toDto);
    }

    /**
     * Delete the anuncio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Anuncio : {}", id);
        anuncioRepository.deleteById(id);
    }
}
