package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.AsistenciaRepository;
import cl.controlclub.myapp.service.AsistenciaService;
import cl.controlclub.myapp.service.dto.AsistenciaDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.Asistencia}.
 */
@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaResource {

    private final Logger log = LoggerFactory.getLogger(AsistenciaResource.class);

    private static final String ENTITY_NAME = "asistencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AsistenciaService asistenciaService;

    private final AsistenciaRepository asistenciaRepository;

    public AsistenciaResource(AsistenciaService asistenciaService, AsistenciaRepository asistenciaRepository) {
        this.asistenciaService = asistenciaService;
        this.asistenciaRepository = asistenciaRepository;
    }

    /**
     * {@code POST  /asistencias} : Create a new asistencia.
     *
     * @param asistenciaDTO the asistenciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new asistenciaDTO, or with status {@code 400 (Bad Request)} if the asistencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AsistenciaDTO> createAsistencia(@Valid @RequestBody AsistenciaDTO asistenciaDTO) throws URISyntaxException {
        log.debug("REST request to save Asistencia : {}", asistenciaDTO);
        if (asistenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new asistencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        asistenciaDTO = asistenciaService.save(asistenciaDTO);
        return ResponseEntity.created(new URI("/api/asistencias/" + asistenciaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, asistenciaDTO.getId().toString()))
            .body(asistenciaDTO);
    }

    /**
     * {@code PUT  /asistencias/:id} : Updates an existing asistencia.
     *
     * @param id the id of the asistenciaDTO to save.
     * @param asistenciaDTO the asistenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asistenciaDTO,
     * or with status {@code 400 (Bad Request)} if the asistenciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the asistenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AsistenciaDTO> updateAsistencia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AsistenciaDTO asistenciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Asistencia : {}, {}", id, asistenciaDTO);
        if (asistenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asistenciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asistenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        asistenciaDTO = asistenciaService.update(asistenciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asistenciaDTO.getId().toString()))
            .body(asistenciaDTO);
    }

    /**
     * {@code PATCH  /asistencias/:id} : Partial updates given fields of an existing asistencia, field will ignore if it is null
     *
     * @param id the id of the asistenciaDTO to save.
     * @param asistenciaDTO the asistenciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asistenciaDTO,
     * or with status {@code 400 (Bad Request)} if the asistenciaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the asistenciaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the asistenciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AsistenciaDTO> partialUpdateAsistencia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AsistenciaDTO asistenciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Asistencia partially : {}, {}", id, asistenciaDTO);
        if (asistenciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asistenciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asistenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AsistenciaDTO> result = asistenciaService.partialUpdate(asistenciaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asistenciaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /asistencias} : get all the asistencias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of asistencias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AsistenciaDTO>> getAllAsistencias(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Asistencias");
        Page<AsistenciaDTO> page = asistenciaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asistencias/:id} : get the "id" asistencia.
     *
     * @param id the id of the asistenciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the asistenciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AsistenciaDTO> getAsistencia(@PathVariable("id") Long id) {
        log.debug("REST request to get Asistencia : {}", id);
        Optional<AsistenciaDTO> asistenciaDTO = asistenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(asistenciaDTO);
    }

    /**
     * {@code DELETE  /asistencias/:id} : delete the "id" asistencia.
     *
     * @param id the id of the asistenciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsistencia(@PathVariable("id") Long id) {
        log.debug("REST request to delete Asistencia : {}", id);
        asistenciaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
