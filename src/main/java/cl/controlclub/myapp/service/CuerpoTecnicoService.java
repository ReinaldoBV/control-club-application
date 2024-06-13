package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.CuerpoTecnico;
import cl.controlclub.myapp.repository.CuerpoTecnicoRepository;
import cl.controlclub.myapp.service.dto.CuerpoTecnicoDTO;
import cl.controlclub.myapp.service.mapper.CuerpoTecnicoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.CuerpoTecnico}.
 */
@Service
@Transactional
public class CuerpoTecnicoService {

    private final Logger log = LoggerFactory.getLogger(CuerpoTecnicoService.class);

    private final CuerpoTecnicoRepository cuerpoTecnicoRepository;

    private final CuerpoTecnicoMapper cuerpoTecnicoMapper;

    public CuerpoTecnicoService(CuerpoTecnicoRepository cuerpoTecnicoRepository, CuerpoTecnicoMapper cuerpoTecnicoMapper) {
        this.cuerpoTecnicoRepository = cuerpoTecnicoRepository;
        this.cuerpoTecnicoMapper = cuerpoTecnicoMapper;
    }

    /**
     * Save a cuerpoTecnico.
     *
     * @param cuerpoTecnicoDTO the entity to save.
     * @return the persisted entity.
     */
    public CuerpoTecnicoDTO save(CuerpoTecnicoDTO cuerpoTecnicoDTO) {
        log.debug("Request to save CuerpoTecnico : {}", cuerpoTecnicoDTO);
        CuerpoTecnico cuerpoTecnico = cuerpoTecnicoMapper.toEntity(cuerpoTecnicoDTO);
        cuerpoTecnico = cuerpoTecnicoRepository.save(cuerpoTecnico);
        return cuerpoTecnicoMapper.toDto(cuerpoTecnico);
    }

    /**
     * Update a cuerpoTecnico.
     *
     * @param cuerpoTecnicoDTO the entity to save.
     * @return the persisted entity.
     */
    public CuerpoTecnicoDTO update(CuerpoTecnicoDTO cuerpoTecnicoDTO) {
        log.debug("Request to update CuerpoTecnico : {}", cuerpoTecnicoDTO);
        CuerpoTecnico cuerpoTecnico = cuerpoTecnicoMapper.toEntity(cuerpoTecnicoDTO);
        cuerpoTecnico = cuerpoTecnicoRepository.save(cuerpoTecnico);
        return cuerpoTecnicoMapper.toDto(cuerpoTecnico);
    }

    /**
     * Partially update a cuerpoTecnico.
     *
     * @param cuerpoTecnicoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CuerpoTecnicoDTO> partialUpdate(CuerpoTecnicoDTO cuerpoTecnicoDTO) {
        log.debug("Request to partially update CuerpoTecnico : {}", cuerpoTecnicoDTO);

        return cuerpoTecnicoRepository
            .findById(cuerpoTecnicoDTO.getId())
            .map(existingCuerpoTecnico -> {
                cuerpoTecnicoMapper.partialUpdate(existingCuerpoTecnico, cuerpoTecnicoDTO);

                return existingCuerpoTecnico;
            })
            .map(cuerpoTecnicoRepository::save)
            .map(cuerpoTecnicoMapper::toDto);
    }

    /**
     * Get all the cuerpoTecnicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CuerpoTecnicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CuerpoTecnicos");
        return cuerpoTecnicoRepository.findAll(pageable).map(cuerpoTecnicoMapper::toDto);
    }

    /**
     * Get one cuerpoTecnico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CuerpoTecnicoDTO> findOne(Long id) {
        log.debug("Request to get CuerpoTecnico : {}", id);
        return cuerpoTecnicoRepository.findById(id).map(cuerpoTecnicoMapper::toDto);
    }

    /**
     * Delete the cuerpoTecnico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CuerpoTecnico : {}", id);
        cuerpoTecnicoRepository.deleteById(id);
    }
}
