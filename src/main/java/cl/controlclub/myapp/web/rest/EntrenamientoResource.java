package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.EntrenamientoRepository;
import cl.controlclub.myapp.service.EntrenamientoService;
import cl.controlclub.myapp.service.dto.EntrenamientoDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.Entrenamiento}.
 */
@RestController
@RequestMapping("/api/entrenamientos")
public class EntrenamientoResource {

    private final Logger log = LoggerFactory.getLogger(EntrenamientoResource.class);

    private static final String ENTITY_NAME = "entrenamiento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntrenamientoService entrenamientoService;

    private final EntrenamientoRepository entrenamientoRepository;

    public EntrenamientoResource(EntrenamientoService entrenamientoService, EntrenamientoRepository entrenamientoRepository) {
        this.entrenamientoService = entrenamientoService;
        this.entrenamientoRepository = entrenamientoRepository;
    }

    /**
     * {@code POST  /entrenamientos} : Create a new entrenamiento.
     *
     * @param entrenamientoDTO the entrenamientoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entrenamientoDTO, or with status {@code 400 (Bad Request)} if the entrenamiento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EntrenamientoDTO> createEntrenamiento(@Valid @RequestBody EntrenamientoDTO entrenamientoDTO)
        throws URISyntaxException {
        log.debug("REST request to save Entrenamiento : {}", entrenamientoDTO);
        if (entrenamientoDTO.getId() != null) {
            throw new BadRequestAlertException("A new entrenamiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        entrenamientoDTO = entrenamientoService.save(entrenamientoDTO);
        return ResponseEntity.created(new URI("/api/entrenamientos/" + entrenamientoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, entrenamientoDTO.getId().toString()))
            .body(entrenamientoDTO);
    }

    /**
     * {@code PUT  /entrenamientos/:id} : Updates an existing entrenamiento.
     *
     * @param id the id of the entrenamientoDTO to save.
     * @param entrenamientoDTO the entrenamientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entrenamientoDTO,
     * or with status {@code 400 (Bad Request)} if the entrenamientoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entrenamientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntrenamientoDTO> updateEntrenamiento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EntrenamientoDTO entrenamientoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Entrenamiento : {}, {}", id, entrenamientoDTO);
        if (entrenamientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entrenamientoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entrenamientoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        entrenamientoDTO = entrenamientoService.update(entrenamientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entrenamientoDTO.getId().toString()))
            .body(entrenamientoDTO);
    }

    /**
     * {@code PATCH  /entrenamientos/:id} : Partial updates given fields of an existing entrenamiento, field will ignore if it is null
     *
     * @param id the id of the entrenamientoDTO to save.
     * @param entrenamientoDTO the entrenamientoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entrenamientoDTO,
     * or with status {@code 400 (Bad Request)} if the entrenamientoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entrenamientoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entrenamientoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntrenamientoDTO> partialUpdateEntrenamiento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EntrenamientoDTO entrenamientoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entrenamiento partially : {}, {}", id, entrenamientoDTO);
        if (entrenamientoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entrenamientoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entrenamientoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntrenamientoDTO> result = entrenamientoService.partialUpdate(entrenamientoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entrenamientoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /entrenamientos} : get all the entrenamientos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entrenamientos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EntrenamientoDTO>> getAllEntrenamientos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Entrenamientos");
        Page<EntrenamientoDTO> page = entrenamientoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entrenamientos/:id} : get the "id" entrenamiento.
     *
     * @param id the id of the entrenamientoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entrenamientoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntrenamientoDTO> getEntrenamiento(@PathVariable("id") Long id) {
        log.debug("REST request to get Entrenamiento : {}", id);
        Optional<EntrenamientoDTO> entrenamientoDTO = entrenamientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entrenamientoDTO);
    }

    /**
     * {@code DELETE  /entrenamientos/:id} : delete the "id" entrenamiento.
     *
     * @param id the id of the entrenamientoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrenamiento(@PathVariable("id") Long id) {
        log.debug("REST request to delete Entrenamiento : {}", id);
        entrenamientoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
