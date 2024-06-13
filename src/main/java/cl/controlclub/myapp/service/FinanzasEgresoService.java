package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.FinanzasEgreso;
import cl.controlclub.myapp.repository.FinanzasEgresoRepository;
import cl.controlclub.myapp.service.dto.FinanzasEgresoDTO;
import cl.controlclub.myapp.service.mapper.FinanzasEgresoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.FinanzasEgreso}.
 */
@Service
@Transactional
public class FinanzasEgresoService {

    private final Logger log = LoggerFactory.getLogger(FinanzasEgresoService.class);

    private final FinanzasEgresoRepository finanzasEgresoRepository;

    private final FinanzasEgresoMapper finanzasEgresoMapper;

    public FinanzasEgresoService(FinanzasEgresoRepository finanzasEgresoRepository, FinanzasEgresoMapper finanzasEgresoMapper) {
        this.finanzasEgresoRepository = finanzasEgresoRepository;
        this.finanzasEgresoMapper = finanzasEgresoMapper;
    }

    /**
     * Save a finanzasEgreso.
     *
     * @param finanzasEgresoDTO the entity to save.
     * @return the persisted entity.
     */
    public FinanzasEgresoDTO save(FinanzasEgresoDTO finanzasEgresoDTO) {
        log.debug("Request to save FinanzasEgreso : {}", finanzasEgresoDTO);
        FinanzasEgreso finanzasEgreso = finanzasEgresoMapper.toEntity(finanzasEgresoDTO);
        finanzasEgreso = finanzasEgresoRepository.save(finanzasEgreso);
        return finanzasEgresoMapper.toDto(finanzasEgreso);
    }

    /**
     * Update a finanzasEgreso.
     *
     * @param finanzasEgresoDTO the entity to save.
     * @return the persisted entity.
     */
    public FinanzasEgresoDTO update(FinanzasEgresoDTO finanzasEgresoDTO) {
        log.debug("Request to update FinanzasEgreso : {}", finanzasEgresoDTO);
        FinanzasEgreso finanzasEgreso = finanzasEgresoMapper.toEntity(finanzasEgresoDTO);
        finanzasEgreso = finanzasEgresoRepository.save(finanzasEgreso);
        return finanzasEgresoMapper.toDto(finanzasEgreso);
    }

    /**
     * Partially update a finanzasEgreso.
     *
     * @param finanzasEgresoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FinanzasEgresoDTO> partialUpdate(FinanzasEgresoDTO finanzasEgresoDTO) {
        log.debug("Request to partially update FinanzasEgreso : {}", finanzasEgresoDTO);

        return finanzasEgresoRepository
            .findById(finanzasEgresoDTO.getId())
            .map(existingFinanzasEgreso -> {
                finanzasEgresoMapper.partialUpdate(existingFinanzasEgreso, finanzasEgresoDTO);

                return existingFinanzasEgreso;
            })
            .map(finanzasEgresoRepository::save)
            .map(finanzasEgresoMapper::toDto);
    }

    /**
     * Get all the finanzasEgresos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FinanzasEgresoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FinanzasEgresos");
        return finanzasEgresoRepository.findAll(pageable).map(finanzasEgresoMapper::toDto);
    }

    /**
     * Get one finanzasEgreso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FinanzasEgresoDTO> findOne(Long id) {
        log.debug("Request to get FinanzasEgreso : {}", id);
        return finanzasEgresoRepository.findById(id).map(finanzasEgresoMapper::toDto);
    }

    /**
     * Delete the finanzasEgreso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FinanzasEgreso : {}", id);
        finanzasEgresoRepository.deleteById(id);
    }
}
