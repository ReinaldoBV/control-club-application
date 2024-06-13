package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.PadreRepository;
import cl.controlclub.myapp.service.PadreService;
import cl.controlclub.myapp.service.dto.PadreDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.Padre}.
 */
@RestController
@RequestMapping("/api/padres")
public class PadreResource {

    private final Logger log = LoggerFactory.getLogger(PadreResource.class);

    private static final String ENTITY_NAME = "padre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PadreService padreService;

    private final PadreRepository padreRepository;

    public PadreResource(PadreService padreService, PadreRepository padreRepository) {
        this.padreService = padreService;
        this.padreRepository = padreRepository;
    }

    /**
     * {@code POST  /padres} : Create a new padre.
     *
     * @param padreDTO the padreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new padreDTO, or with status {@code 400 (Bad Request)} if the padre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PadreDTO> createPadre(@Valid @RequestBody PadreDTO padreDTO) throws URISyntaxException {
        log.debug("REST request to save Padre : {}", padreDTO);
        if (padreDTO.getId() != null) {
            throw new BadRequestAlertException("A new padre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        padreDTO = padreService.save(padreDTO);
        return ResponseEntity.created(new URI("/api/padres/" + padreDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, padreDTO.getId().toString()))
            .body(padreDTO);
    }

    /**
     * {@code PUT  /padres/:id} : Updates an existing padre.
     *
     * @param id the id of the padreDTO to save.
     * @param padreDTO the padreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated padreDTO,
     * or with status {@code 400 (Bad Request)} if the padreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the padreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PadreDTO> updatePadre(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PadreDTO padreDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Padre : {}, {}", id, padreDTO);
        if (padreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, padreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!padreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        padreDTO = padreService.update(padreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, padreDTO.getId().toString()))
            .body(padreDTO);
    }

    /**
     * {@code PATCH  /padres/:id} : Partial updates given fields of an existing padre, field will ignore if it is null
     *
     * @param id the id of the padreDTO to save.
     * @param padreDTO the padreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated padreDTO,
     * or with status {@code 400 (Bad Request)} if the padreDTO is not valid,
     * or with status {@code 404 (Not Found)} if the padreDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the padreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PadreDTO> partialUpdatePadre(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PadreDTO padreDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Padre partially : {}, {}", id, padreDTO);
        if (padreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, padreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!padreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PadreDTO> result = padreService.partialUpdate(padreDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, padreDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /padres} : get all the padres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of padres in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PadreDTO>> getAllPadres(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Padres");
        Page<PadreDTO> page = padreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /padres/:id} : get the "id" padre.
     *
     * @param id the id of the padreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the padreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PadreDTO> getPadre(@PathVariable("id") Long id) {
        log.debug("REST request to get Padre : {}", id);
        Optional<PadreDTO> padreDTO = padreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(padreDTO);
    }

    /**
     * {@code DELETE  /padres/:id} : delete the "id" padre.
     *
     * @param id the id of the padreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePadre(@PathVariable("id") Long id) {
        log.debug("REST request to delete Padre : {}", id);
        padreService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
