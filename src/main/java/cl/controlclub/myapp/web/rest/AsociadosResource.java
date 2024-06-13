package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.AsociadosRepository;
import cl.controlclub.myapp.service.AsociadosService;
import cl.controlclub.myapp.service.dto.AsociadosDTO;
import cl.controlclub.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cl.controlclub.myapp.domain.Asociados}.
 */
@RestController
@RequestMapping("/api/asociados")
public class AsociadosResource {

    private final Logger log = LoggerFactory.getLogger(AsociadosResource.class);

    private static final String ENTITY_NAME = "asociados";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AsociadosService asociadosService;

    private final AsociadosRepository asociadosRepository;

    public AsociadosResource(AsociadosService asociadosService, AsociadosRepository asociadosRepository) {
        this.asociadosService = asociadosService;
        this.asociadosRepository = asociadosRepository;
    }

    /**
     * {@code POST  /asociados} : Create a new asociados.
     *
     * @param asociadosDTO the asociadosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new asociadosDTO, or with status {@code 400 (Bad Request)} if the asociados has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AsociadosDTO> createAsociados(@Valid @RequestBody AsociadosDTO asociadosDTO) throws URISyntaxException {
        log.debug("REST request to save Asociados : {}", asociadosDTO);
        if (asociadosDTO.getId() != null) {
            throw new BadRequestAlertException("A new asociados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        asociadosDTO = asociadosService.save(asociadosDTO);
        return ResponseEntity.created(new URI("/api/asociados/" + asociadosDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, asociadosDTO.getId().toString()))
            .body(asociadosDTO);
    }

    /**
     * {@code PUT  /asociados/:id} : Updates an existing asociados.
     *
     * @param id the id of the asociadosDTO to save.
     * @param asociadosDTO the asociadosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asociadosDTO,
     * or with status {@code 400 (Bad Request)} if the asociadosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the asociadosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AsociadosDTO> updateAsociados(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AsociadosDTO asociadosDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Asociados : {}, {}", id, asociadosDTO);
        if (asociadosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asociadosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asociadosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        asociadosDTO = asociadosService.update(asociadosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asociadosDTO.getId().toString()))
            .body(asociadosDTO);
    }

    /**
     * {@code PATCH  /asociados/:id} : Partial updates given fields of an existing asociados, field will ignore if it is null
     *
     * @param id the id of the asociadosDTO to save.
     * @param asociadosDTO the asociadosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asociadosDTO,
     * or with status {@code 400 (Bad Request)} if the asociadosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the asociadosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the asociadosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AsociadosDTO> partialUpdateAsociados(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AsociadosDTO asociadosDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Asociados partially : {}, {}", id, asociadosDTO);
        if (asociadosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asociadosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asociadosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AsociadosDTO> result = asociadosService.partialUpdate(asociadosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asociadosDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /asociados} : get all the asociados.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of asociados in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AsociadosDTO>> getAllAsociados(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Asociados");
        Page<AsociadosDTO> page = asociadosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asociados/:id} : get the "id" asociados.
     *
     * @param id the id of the asociadosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the asociadosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AsociadosDTO> getAsociados(@PathVariable("id") Long id) {
        log.debug("REST request to get Asociados : {}", id);
        Optional<AsociadosDTO> asociadosDTO = asociadosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(asociadosDTO);
    }

    /**
     * {@code DELETE  /asociados/:id} : delete the "id" asociados.
     *
     * @param id the id of the asociadosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsociados(@PathVariable("id") Long id) {
        log.debug("REST request to delete Asociados : {}", id);
        asociadosService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
