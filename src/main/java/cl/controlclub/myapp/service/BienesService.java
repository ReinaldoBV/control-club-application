package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Bienes;
import cl.controlclub.myapp.repository.BienesRepository;
import cl.controlclub.myapp.service.dto.BienesDTO;
import cl.controlclub.myapp.service.mapper.BienesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Bienes}.
 */
@Service
@Transactional
public class BienesService {

    private final Logger log = LoggerFactory.getLogger(BienesService.class);

    private final BienesRepository bienesRepository;

    private final BienesMapper bienesMapper;

    public BienesService(BienesRepository bienesRepository, BienesMapper bienesMapper) {
        this.bienesRepository = bienesRepository;
        this.bienesMapper = bienesMapper;
    }

    /**
     * Save a bienes.
     *
     * @param bienesDTO the entity to save.
     * @return the persisted entity.
     */
    public BienesDTO save(BienesDTO bienesDTO) {
        log.debug("Request to save Bienes : {}", bienesDTO);
        Bienes bienes = bienesMapper.toEntity(bienesDTO);
        bienes = bienesRepository.save(bienes);
        return bienesMapper.toDto(bienes);
    }

    /**
     * Update a bienes.
     *
     * @param bienesDTO the entity to save.
     * @return the persisted entity.
     */
    public BienesDTO update(BienesDTO bienesDTO) {
        log.debug("Request to update Bienes : {}", bienesDTO);
        Bienes bienes = bienesMapper.toEntity(bienesDTO);
        bienes = bienesRepository.save(bienes);
        return bienesMapper.toDto(bienes);
    }

    /**
     * Partially update a bienes.
     *
     * @param bienesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BienesDTO> partialUpdate(BienesDTO bienesDTO) {
        log.debug("Request to partially update Bienes : {}", bienesDTO);

        return bienesRepository
            .findById(bienesDTO.getId())
            .map(existingBienes -> {
                bienesMapper.partialUpdate(existingBienes, bienesDTO);

                return existingBienes;
            })
            .map(bienesRepository::save)
            .map(bienesMapper::toDto);
    }

    /**
     * Get all the bienes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BienesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bienes");
        return bienesRepository.findAll(pageable).map(bienesMapper::toDto);
    }

    /**
     * Get one bienes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BienesDTO> findOne(Long id) {
        log.debug("Request to get Bienes : {}", id);
        return bienesRepository.findById(id).map(bienesMapper::toDto);
    }

    /**
     * Delete the bienes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bienes : {}", id);
        bienesRepository.deleteById(id);
    }
}
