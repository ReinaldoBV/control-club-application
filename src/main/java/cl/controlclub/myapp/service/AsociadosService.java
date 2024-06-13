package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Asociados;
import cl.controlclub.myapp.repository.AsociadosRepository;
import cl.controlclub.myapp.service.dto.AsociadosDTO;
import cl.controlclub.myapp.service.mapper.AsociadosMapper;
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
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Asociados}.
 */
@Service
@Transactional
public class AsociadosService {

    private final Logger log = LoggerFactory.getLogger(AsociadosService.class);

    private final AsociadosRepository asociadosRepository;

    private final AsociadosMapper asociadosMapper;

    public AsociadosService(AsociadosRepository asociadosRepository, AsociadosMapper asociadosMapper) {
        this.asociadosRepository = asociadosRepository;
        this.asociadosMapper = asociadosMapper;
    }

    /**
     * Save a asociados.
     *
     * @param asociadosDTO the entity to save.
     * @return the persisted entity.
     */
    public AsociadosDTO save(AsociadosDTO asociadosDTO) {
        log.debug("Request to save Asociados : {}", asociadosDTO);
        Asociados asociados = asociadosMapper.toEntity(asociadosDTO);
        asociados = asociadosRepository.save(asociados);
        return asociadosMapper.toDto(asociados);
    }

    /**
     * Update a asociados.
     *
     * @param asociadosDTO the entity to save.
     * @return the persisted entity.
     */
    public AsociadosDTO update(AsociadosDTO asociadosDTO) {
        log.debug("Request to update Asociados : {}", asociadosDTO);
        Asociados asociados = asociadosMapper.toEntity(asociadosDTO);
        asociados = asociadosRepository.save(asociados);
        return asociadosMapper.toDto(asociados);
    }

    /**
     * Partially update a asociados.
     *
     * @param asociadosDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AsociadosDTO> partialUpdate(AsociadosDTO asociadosDTO) {
        log.debug("Request to partially update Asociados : {}", asociadosDTO);

        return asociadosRepository
            .findById(asociadosDTO.getId())
            .map(existingAsociados -> {
                asociadosMapper.partialUpdate(existingAsociados, asociadosDTO);

                return existingAsociados;
            })
            .map(asociadosRepository::save)
            .map(asociadosMapper::toDto);
    }

    /**
     * Get all the asociados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AsociadosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Asociados");
        return asociadosRepository.findAll(pageable).map(asociadosMapper::toDto);
    }

    /**
     *  Get all the asociados where Usuario is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AsociadosDTO> findAllWhereUsuarioIsNull() {
        log.debug("Request to get all asociados where Usuario is null");
        return StreamSupport.stream(asociadosRepository.findAll().spliterator(), false)
            .filter(asociados -> asociados.getUsuario() == null)
            .map(asociadosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the asociados where Directivos is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AsociadosDTO> findAllWhereDirectivosIsNull() {
        log.debug("Request to get all asociados where Directivos is null");
        return StreamSupport.stream(asociadosRepository.findAll().spliterator(), false)
            .filter(asociados -> asociados.getDirectivos() == null)
            .map(asociadosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the asociados where CuerpoTecnico is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AsociadosDTO> findAllWhereCuerpoTecnicoIsNull() {
        log.debug("Request to get all asociados where CuerpoTecnico is null");
        return StreamSupport.stream(asociadosRepository.findAll().spliterator(), false)
            .filter(asociados -> asociados.getCuerpoTecnico() == null)
            .map(asociadosMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one asociados by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AsociadosDTO> findOne(Long id) {
        log.debug("Request to get Asociados : {}", id);
        return asociadosRepository.findById(id).map(asociadosMapper::toDto);
    }

    /**
     * Delete the asociados by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Asociados : {}", id);
        asociadosRepository.deleteById(id);
    }
}
