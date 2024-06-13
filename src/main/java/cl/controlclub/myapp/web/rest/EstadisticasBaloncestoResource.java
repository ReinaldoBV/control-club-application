package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.EstadisticasBaloncestoRepository;
import cl.controlclub.myapp.service.EstadisticasBaloncestoService;
import cl.controlclub.myapp.service.dto.EstadisticasBaloncestoDTO;
import cl.controlclub.myapp.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.EstadisticasBaloncesto}.
 */
@RestController
@RequestMapping("/api/estadisticas-baloncestos")
public class EstadisticasBaloncestoResource {

    private final Logger log = LoggerFactory.getLogger(EstadisticasBaloncestoResource.class);

    private static final String ENTITY_NAME = "estadisticasBaloncesto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadisticasBaloncestoService estadisticasBaloncestoService;

    private final EstadisticasBaloncestoRepository estadisticasBaloncestoRepository;

    public EstadisticasBaloncestoResource(
        EstadisticasBaloncestoService estadisticasBaloncestoService,
        EstadisticasBaloncestoRepository estadisticasBaloncestoRepository
    ) {
        this.estadisticasBaloncestoService = estadisticasBaloncestoService;
        this.estadisticasBaloncestoRepository = estadisticasBaloncestoRepository;
    }

    /**
     * {@code POST  /estadisticas-baloncestos} : Create a new estadisticasBaloncesto.
     *
     * @param estadisticasBaloncestoDTO the estadisticasBaloncestoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadisticasBaloncestoDTO, or with status {@code 400 (Bad Request)} if the estadisticasBaloncesto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EstadisticasBaloncestoDTO> createEstadisticasBaloncesto(
        @RequestBody EstadisticasBaloncestoDTO estadisticasBaloncestoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EstadisticasBaloncesto : {}", estadisticasBaloncestoDTO);
        if (estadisticasBaloncestoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estadisticasBaloncesto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        estadisticasBaloncestoDTO = estadisticasBaloncestoService.save(estadisticasBaloncestoDTO);
        return ResponseEntity.created(new URI("/api/estadisticas-baloncestos/" + estadisticasBaloncestoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, estadisticasBaloncestoDTO.getId().toString()))
            .body(estadisticasBaloncestoDTO);
    }

    /**
     * {@code PUT  /estadisticas-baloncestos/:id} : Updates an existing estadisticasBaloncesto.
     *
     * @param id the id of the estadisticasBaloncestoDTO to save.
     * @param estadisticasBaloncestoDTO the estadisticasBaloncestoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadisticasBaloncestoDTO,
     * or with status {@code 400 (Bad Request)} if the estadisticasBaloncestoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadisticasBaloncestoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EstadisticasBaloncestoDTO> updateEstadisticasBaloncesto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstadisticasBaloncestoDTO estadisticasBaloncestoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EstadisticasBaloncesto : {}, {}", id, estadisticasBaloncestoDTO);
        if (estadisticasBaloncestoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadisticasBaloncestoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadisticasBaloncestoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        estadisticasBaloncestoDTO = estadisticasBaloncestoService.update(estadisticasBaloncestoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadisticasBaloncestoDTO.getId().toString()))
            .body(estadisticasBaloncestoDTO);
    }

    /**
     * {@code PATCH  /estadisticas-baloncestos/:id} : Partial updates given fields of an existing estadisticasBaloncesto, field will ignore if it is null
     *
     * @param id the id of the estadisticasBaloncestoDTO to save.
     * @param estadisticasBaloncestoDTO the estadisticasBaloncestoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadisticasBaloncestoDTO,
     * or with status {@code 400 (Bad Request)} if the estadisticasBaloncestoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the estadisticasBaloncestoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the estadisticasBaloncestoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstadisticasBaloncestoDTO> partialUpdateEstadisticasBaloncesto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstadisticasBaloncestoDTO estadisticasBaloncestoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstadisticasBaloncesto partially : {}, {}", id, estadisticasBaloncestoDTO);
        if (estadisticasBaloncestoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadisticasBaloncestoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadisticasBaloncestoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstadisticasBaloncestoDTO> result = estadisticasBaloncestoService.partialUpdate(estadisticasBaloncestoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadisticasBaloncestoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /estadisticas-baloncestos} : get all the estadisticasBaloncestos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadisticasBaloncestos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EstadisticasBaloncestoDTO>> getAllEstadisticasBaloncestos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of EstadisticasBaloncestos");
        Page<EstadisticasBaloncestoDTO> page = estadisticasBaloncestoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estadisticas-baloncestos/:id} : get the "id" estadisticasBaloncesto.
     *
     * @param id the id of the estadisticasBaloncestoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadisticasBaloncestoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EstadisticasBaloncestoDTO> getEstadisticasBaloncesto(@PathVariable("id") Long id) {
        log.debug("REST request to get EstadisticasBaloncesto : {}", id);
        Optional<EstadisticasBaloncestoDTO> estadisticasBaloncestoDTO = estadisticasBaloncestoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadisticasBaloncestoDTO);
    }

    /**
     * {@code DELETE  /estadisticas-baloncestos/:id} : delete the "id" estadisticasBaloncesto.
     *
     * @param id the id of the estadisticasBaloncestoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstadisticasBaloncesto(@PathVariable("id") Long id) {
        log.debug("REST request to delete EstadisticasBaloncesto : {}", id);
        estadisticasBaloncestoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
