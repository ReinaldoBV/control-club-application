package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Cuentas;
import cl.controlclub.myapp.repository.CuentasRepository;
import cl.controlclub.myapp.service.dto.CuentasDTO;
import cl.controlclub.myapp.service.mapper.CuentasMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Cuentas}.
 */
@Service
@Transactional
public class CuentasService {

    private final Logger log = LoggerFactory.getLogger(CuentasService.class);

    private final CuentasRepository cuentasRepository;

    private final CuentasMapper cuentasMapper;

    public CuentasService(CuentasRepository cuentasRepository, CuentasMapper cuentasMapper) {
        this.cuentasRepository = cuentasRepository;
        this.cuentasMapper = cuentasMapper;
    }

    /**
     * Save a cuentas.
     *
     * @param cuentasDTO the entity to save.
     * @return the persisted entity.
     */
    public CuentasDTO save(CuentasDTO cuentasDTO) {
        log.debug("Request to save Cuentas : {}", cuentasDTO);
        Cuentas cuentas = cuentasMapper.toEntity(cuentasDTO);
        cuentas = cuentasRepository.save(cuentas);
        return cuentasMapper.toDto(cuentas);
    }

    /**
     * Update a cuentas.
     *
     * @param cuentasDTO the entity to save.
     * @return the persisted entity.
     */
    public CuentasDTO update(CuentasDTO cuentasDTO) {
        log.debug("Request to update Cuentas : {}", cuentasDTO);
        Cuentas cuentas = cuentasMapper.toEntity(cuentasDTO);
        cuentas = cuentasRepository.save(cuentas);
        return cuentasMapper.toDto(cuentas);
    }

    /**
     * Partially update a cuentas.
     *
     * @param cuentasDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CuentasDTO> partialUpdate(CuentasDTO cuentasDTO) {
        log.debug("Request to partially update Cuentas : {}", cuentasDTO);

        return cuentasRepository
            .findById(cuentasDTO.getId())
            .map(existingCuentas -> {
                cuentasMapper.partialUpdate(existingCuentas, cuentasDTO);

                return existingCuentas;
            })
            .map(cuentasRepository::save)
            .map(cuentasMapper::toDto);
    }

    /**
     * Get all the cuentas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CuentasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cuentas");
        return cuentasRepository.findAll(pageable).map(cuentasMapper::toDto);
    }

    /**
     * Get one cuentas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CuentasDTO> findOne(Long id) {
        log.debug("Request to get Cuentas : {}", id);
        return cuentasRepository.findById(id).map(cuentasMapper::toDto);
    }

    /**
     * Delete the cuentas by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cuentas : {}", id);
        cuentasRepository.deleteById(id);
    }
}
