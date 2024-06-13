package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.FinanzasIngreso;
import cl.controlclub.myapp.repository.FinanzasIngresoRepository;
import cl.controlclub.myapp.service.dto.FinanzasIngresoDTO;
import cl.controlclub.myapp.service.mapper.FinanzasIngresoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.FinanzasIngreso}.
 */
@Service
@Transactional
public class FinanzasIngresoService {

    private final Logger log = LoggerFactory.getLogger(FinanzasIngresoService.class);

    private final FinanzasIngresoRepository finanzasIngresoRepository;

    private final FinanzasIngresoMapper finanzasIngresoMapper;

    public FinanzasIngresoService(FinanzasIngresoRepository finanzasIngresoRepository, FinanzasIngresoMapper finanzasIngresoMapper) {
        this.finanzasIngresoRepository = finanzasIngresoRepository;
        this.finanzasIngresoMapper = finanzasIngresoMapper;
    }

    /**
     * Save a finanzasIngreso.
     *
     * @param finanzasIngresoDTO the entity to save.
     * @return the persisted entity.
     */
    public FinanzasIngresoDTO save(FinanzasIngresoDTO finanzasIngresoDTO) {
        log.debug("Request to save FinanzasIngreso : {}", finanzasIngresoDTO);
        FinanzasIngreso finanzasIngreso = finanzasIngresoMapper.toEntity(finanzasIngresoDTO);
        finanzasIngreso = finanzasIngresoRepository.save(finanzasIngreso);
        return finanzasIngresoMapper.toDto(finanzasIngreso);
    }

    /**
     * Update a finanzasIngreso.
     *
     * @param finanzasIngresoDTO the entity to save.
     * @return the persisted entity.
     */
    public FinanzasIngresoDTO update(FinanzasIngresoDTO finanzasIngresoDTO) {
        log.debug("Request to update FinanzasIngreso : {}", finanzasIngresoDTO);
        FinanzasIngreso finanzasIngreso = finanzasIngresoMapper.toEntity(finanzasIngresoDTO);
        finanzasIngreso = finanzasIngresoRepository.save(finanzasIngreso);
        return finanzasIngresoMapper.toDto(finanzasIngreso);
    }

    /**
     * Partially update a finanzasIngreso.
     *
     * @param finanzasIngresoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FinanzasIngresoDTO> partialUpdate(FinanzasIngresoDTO finanzasIngresoDTO) {
        log.debug("Request to partially update FinanzasIngreso : {}", finanzasIngresoDTO);

        return finanzasIngresoRepository
            .findById(finanzasIngresoDTO.getId())
            .map(existingFinanzasIngreso -> {
                finanzasIngresoMapper.partialUpdate(existingFinanzasIngreso, finanzasIngresoDTO);

                return existingFinanzasIngreso;
            })
            .map(finanzasIngresoRepository::save)
            .map(finanzasIngresoMapper::toDto);
    }

    /**
     * Get all the finanzasIngresos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FinanzasIngresoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FinanzasIngresos");
        return finanzasIngresoRepository.findAll(pageable).map(finanzasIngresoMapper::toDto);
    }

    /**
     * Get one finanzasIngreso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FinanzasIngresoDTO> findOne(Long id) {
        log.debug("Request to get FinanzasIngreso : {}", id);
        return finanzasIngresoRepository.findById(id).map(finanzasIngresoMapper::toDto);
    }

    /**
     * Delete the finanzasIngreso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FinanzasIngreso : {}", id);
        finanzasIngresoRepository.deleteById(id);
    }
}
