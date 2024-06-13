package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.ComunaRepository;
import cl.controlclub.myapp.service.ComunaService;
import cl.controlclub.myapp.service.dto.ComunaDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.Comuna}.
 */
@RestController
@RequestMapping("/api/comunas")
public class ComunaResource {

    private final Logger log = LoggerFactory.getLogger(ComunaResource.class);

    private static final String ENTITY_NAME = "comuna";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComunaService comunaService;

    private final ComunaRepository comunaRepository;

    public ComunaResource(ComunaService comunaService, ComunaRepository comunaRepository) {
        this.comunaService = comunaService;
        this.comunaRepository = comunaRepository;
    }

    /**
     * {@code POST  /comunas} : Create a new comuna.
     *
     * @param comunaDTO the comunaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comunaDTO, or with status {@code 400 (Bad Request)} if the comuna has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ComunaDTO> createComuna(@Valid @RequestBody ComunaDTO comunaDTO) throws URISyntaxException {
        log.debug("REST request to save Comuna : {}", comunaDTO);
        if (comunaDTO.getId() != null) {
            throw new BadRequestAlertException("A new comuna cannot already have an ID", ENTITY_NAME, "idexists");
        }
        comunaDTO = comunaService.save(comunaDTO);
        return ResponseEntity.created(new URI("/api/comunas/" + comunaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, comunaDTO.getId().toString()))
            .body(comunaDTO);
    }

    /**
     * {@code PUT  /comunas/:id} : Updates an existing comuna.
     *
     * @param id the id of the comunaDTO to save.
     * @param comunaDTO the comunaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comunaDTO,
     * or with status {@code 400 (Bad Request)} if the comunaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comunaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ComunaDTO> updateComuna(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ComunaDTO comunaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Comuna : {}, {}", id, comunaDTO);
        if (comunaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comunaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comunaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        comunaDTO = comunaService.update(comunaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comunaDTO.getId().toString()))
            .body(comunaDTO);
    }

    /**
     * {@code PATCH  /comunas/:id} : Partial updates given fields of an existing comuna, field will ignore if it is null
     *
     * @param id the id of the comunaDTO to save.
     * @param comunaDTO the comunaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comunaDTO,
     * or with status {@code 400 (Bad Request)} if the comunaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the comunaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the comunaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ComunaDTO> partialUpdateComuna(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ComunaDTO comunaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Comuna partially : {}, {}", id, comunaDTO);
        if (comunaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comunaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comunaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComunaDTO> result = comunaService.partialUpdate(comunaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comunaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /comunas} : get all the comunas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comunas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ComunaDTO>> getAllComunas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Comunas");
        Page<ComunaDTO> page = comunaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comunas/:id} : get the "id" comuna.
     *
     * @param id the id of the comunaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comunaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ComunaDTO> getComuna(@PathVariable("id") Long id) {
        log.debug("REST request to get Comuna : {}", id);
        Optional<ComunaDTO> comunaDTO = comunaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comunaDTO);
    }

    /**
     * {@code DELETE  /comunas/:id} : delete the "id" comuna.
     *
     * @param id the id of the comunaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComuna(@PathVariable("id") Long id) {
        log.debug("REST request to delete Comuna : {}", id);
        comunaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
