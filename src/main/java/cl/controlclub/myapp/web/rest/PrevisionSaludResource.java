package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.PrevisionSaludRepository;
import cl.controlclub.myapp.service.PrevisionSaludService;
import cl.controlclub.myapp.service.dto.PrevisionSaludDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.PrevisionSalud}.
 */
@RestController
@RequestMapping("/api/prevision-saluds")
public class PrevisionSaludResource {

    private final Logger log = LoggerFactory.getLogger(PrevisionSaludResource.class);

    private static final String ENTITY_NAME = "previsionSalud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrevisionSaludService previsionSaludService;

    private final PrevisionSaludRepository previsionSaludRepository;

    public PrevisionSaludResource(PrevisionSaludService previsionSaludService, PrevisionSaludRepository previsionSaludRepository) {
        this.previsionSaludService = previsionSaludService;
        this.previsionSaludRepository = previsionSaludRepository;
    }

    /**
     * {@code POST  /prevision-saluds} : Create a new previsionSalud.
     *
     * @param previsionSaludDTO the previsionSaludDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new previsionSaludDTO, or with status {@code 400 (Bad Request)} if the previsionSalud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PrevisionSaludDTO> createPrevisionSalud(@Valid @RequestBody PrevisionSaludDTO previsionSaludDTO)
        throws URISyntaxException {
        log.debug("REST request to save PrevisionSalud : {}", previsionSaludDTO);
        if (previsionSaludDTO.getId() != null) {
            throw new BadRequestAlertException("A new previsionSalud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        previsionSaludDTO = previsionSaludService.save(previsionSaludDTO);
        return ResponseEntity.created(new URI("/api/prevision-saluds/" + previsionSaludDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, previsionSaludDTO.getId().toString()))
            .body(previsionSaludDTO);
    }

    /**
     * {@code PUT  /prevision-saluds/:id} : Updates an existing previsionSalud.
     *
     * @param id the id of the previsionSaludDTO to save.
     * @param previsionSaludDTO the previsionSaludDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated previsionSaludDTO,
     * or with status {@code 400 (Bad Request)} if the previsionSaludDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the previsionSaludDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PrevisionSaludDTO> updatePrevisionSalud(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PrevisionSaludDTO previsionSaludDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PrevisionSalud : {}, {}", id, previsionSaludDTO);
        if (previsionSaludDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, previsionSaludDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!previsionSaludRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        previsionSaludDTO = previsionSaludService.update(previsionSaludDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, previsionSaludDTO.getId().toString()))
            .body(previsionSaludDTO);
    }

    /**
     * {@code PATCH  /prevision-saluds/:id} : Partial updates given fields of an existing previsionSalud, field will ignore if it is null
     *
     * @param id the id of the previsionSaludDTO to save.
     * @param previsionSaludDTO the previsionSaludDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated previsionSaludDTO,
     * or with status {@code 400 (Bad Request)} if the previsionSaludDTO is not valid,
     * or with status {@code 404 (Not Found)} if the previsionSaludDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the previsionSaludDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrevisionSaludDTO> partialUpdatePrevisionSalud(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PrevisionSaludDTO previsionSaludDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrevisionSalud partially : {}, {}", id, previsionSaludDTO);
        if (previsionSaludDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, previsionSaludDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!previsionSaludRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrevisionSaludDTO> result = previsionSaludService.partialUpdate(previsionSaludDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, previsionSaludDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /prevision-saluds} : get all the previsionSaluds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of previsionSaluds in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PrevisionSaludDTO>> getAllPrevisionSaluds(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PrevisionSaluds");
        Page<PrevisionSaludDTO> page = previsionSaludService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prevision-saluds/:id} : get the "id" previsionSalud.
     *
     * @param id the id of the previsionSaludDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the previsionSaludDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PrevisionSaludDTO> getPrevisionSalud(@PathVariable("id") Long id) {
        log.debug("REST request to get PrevisionSalud : {}", id);
        Optional<PrevisionSaludDTO> previsionSaludDTO = previsionSaludService.findOne(id);
        return ResponseUtil.wrapOrNotFound(previsionSaludDTO);
    }

    /**
     * {@code DELETE  /prevision-saluds/:id} : delete the "id" previsionSalud.
     *
     * @param id the id of the previsionSaludDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrevisionSalud(@PathVariable("id") Long id) {
        log.debug("REST request to delete PrevisionSalud : {}", id);
        previsionSaludService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
