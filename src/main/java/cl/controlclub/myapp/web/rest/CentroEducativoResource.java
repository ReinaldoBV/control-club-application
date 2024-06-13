package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.CentroEducativoRepository;
import cl.controlclub.myapp.service.CentroEducativoService;
import cl.controlclub.myapp.service.dto.CentroEducativoDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.CentroEducativo}.
 */
@RestController
@RequestMapping("/api/centro-educativos")
public class CentroEducativoResource {

    private final Logger log = LoggerFactory.getLogger(CentroEducativoResource.class);

    private static final String ENTITY_NAME = "centroEducativo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentroEducativoService centroEducativoService;

    private final CentroEducativoRepository centroEducativoRepository;

    public CentroEducativoResource(CentroEducativoService centroEducativoService, CentroEducativoRepository centroEducativoRepository) {
        this.centroEducativoService = centroEducativoService;
        this.centroEducativoRepository = centroEducativoRepository;
    }

    /**
     * {@code POST  /centro-educativos} : Create a new centroEducativo.
     *
     * @param centroEducativoDTO the centroEducativoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centroEducativoDTO, or with status {@code 400 (Bad Request)} if the centroEducativo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CentroEducativoDTO> createCentroEducativo(@Valid @RequestBody CentroEducativoDTO centroEducativoDTO)
        throws URISyntaxException {
        log.debug("REST request to save CentroEducativo : {}", centroEducativoDTO);
        if (centroEducativoDTO.getId() != null) {
            throw new BadRequestAlertException("A new centroEducativo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        centroEducativoDTO = centroEducativoService.save(centroEducativoDTO);
        return ResponseEntity.created(new URI("/api/centro-educativos/" + centroEducativoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, centroEducativoDTO.getId().toString()))
            .body(centroEducativoDTO);
    }

    /**
     * {@code PUT  /centro-educativos/:id} : Updates an existing centroEducativo.
     *
     * @param id the id of the centroEducativoDTO to save.
     * @param centroEducativoDTO the centroEducativoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroEducativoDTO,
     * or with status {@code 400 (Bad Request)} if the centroEducativoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centroEducativoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CentroEducativoDTO> updateCentroEducativo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CentroEducativoDTO centroEducativoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CentroEducativo : {}, {}", id, centroEducativoDTO);
        if (centroEducativoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centroEducativoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centroEducativoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        centroEducativoDTO = centroEducativoService.update(centroEducativoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centroEducativoDTO.getId().toString()))
            .body(centroEducativoDTO);
    }

    /**
     * {@code PATCH  /centro-educativos/:id} : Partial updates given fields of an existing centroEducativo, field will ignore if it is null
     *
     * @param id the id of the centroEducativoDTO to save.
     * @param centroEducativoDTO the centroEducativoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroEducativoDTO,
     * or with status {@code 400 (Bad Request)} if the centroEducativoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the centroEducativoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the centroEducativoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CentroEducativoDTO> partialUpdateCentroEducativo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CentroEducativoDTO centroEducativoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CentroEducativo partially : {}, {}", id, centroEducativoDTO);
        if (centroEducativoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centroEducativoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centroEducativoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CentroEducativoDTO> result = centroEducativoService.partialUpdate(centroEducativoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centroEducativoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /centro-educativos} : get all the centroEducativos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centroEducativos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CentroEducativoDTO>> getAllCentroEducativos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CentroEducativos");
        Page<CentroEducativoDTO> page = centroEducativoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /centro-educativos/:id} : get the "id" centroEducativo.
     *
     * @param id the id of the centroEducativoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centroEducativoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CentroEducativoDTO> getCentroEducativo(@PathVariable("id") Long id) {
        log.debug("REST request to get CentroEducativo : {}", id);
        Optional<CentroEducativoDTO> centroEducativoDTO = centroEducativoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centroEducativoDTO);
    }

    /**
     * {@code DELETE  /centro-educativos/:id} : delete the "id" centroEducativo.
     *
     * @param id the id of the centroEducativoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCentroEducativo(@PathVariable("id") Long id) {
        log.debug("REST request to delete CentroEducativo : {}", id);
        centroEducativoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
