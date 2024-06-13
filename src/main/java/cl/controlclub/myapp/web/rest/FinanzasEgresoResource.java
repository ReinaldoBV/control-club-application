package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.FinanzasEgresoRepository;
import cl.controlclub.myapp.service.FinanzasEgresoService;
import cl.controlclub.myapp.service.dto.FinanzasEgresoDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.FinanzasEgreso}.
 */
@RestController
@RequestMapping("/api/finanzas-egresos")
public class FinanzasEgresoResource {

    private final Logger log = LoggerFactory.getLogger(FinanzasEgresoResource.class);

    private static final String ENTITY_NAME = "finanzasEgreso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FinanzasEgresoService finanzasEgresoService;

    private final FinanzasEgresoRepository finanzasEgresoRepository;

    public FinanzasEgresoResource(FinanzasEgresoService finanzasEgresoService, FinanzasEgresoRepository finanzasEgresoRepository) {
        this.finanzasEgresoService = finanzasEgresoService;
        this.finanzasEgresoRepository = finanzasEgresoRepository;
    }

    /**
     * {@code POST  /finanzas-egresos} : Create a new finanzasEgreso.
     *
     * @param finanzasEgresoDTO the finanzasEgresoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new finanzasEgresoDTO, or with status {@code 400 (Bad Request)} if the finanzasEgreso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FinanzasEgresoDTO> createFinanzasEgreso(@Valid @RequestBody FinanzasEgresoDTO finanzasEgresoDTO)
        throws URISyntaxException {
        log.debug("REST request to save FinanzasEgreso : {}", finanzasEgresoDTO);
        if (finanzasEgresoDTO.getId() != null) {
            throw new BadRequestAlertException("A new finanzasEgreso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        finanzasEgresoDTO = finanzasEgresoService.save(finanzasEgresoDTO);
        return ResponseEntity.created(new URI("/api/finanzas-egresos/" + finanzasEgresoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, finanzasEgresoDTO.getId().toString()))
            .body(finanzasEgresoDTO);
    }

    /**
     * {@code PUT  /finanzas-egresos/:id} : Updates an existing finanzasEgreso.
     *
     * @param id the id of the finanzasEgresoDTO to save.
     * @param finanzasEgresoDTO the finanzasEgresoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated finanzasEgresoDTO,
     * or with status {@code 400 (Bad Request)} if the finanzasEgresoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the finanzasEgresoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FinanzasEgresoDTO> updateFinanzasEgreso(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FinanzasEgresoDTO finanzasEgresoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FinanzasEgreso : {}, {}", id, finanzasEgresoDTO);
        if (finanzasEgresoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, finanzasEgresoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!finanzasEgresoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        finanzasEgresoDTO = finanzasEgresoService.update(finanzasEgresoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, finanzasEgresoDTO.getId().toString()))
            .body(finanzasEgresoDTO);
    }

    /**
     * {@code PATCH  /finanzas-egresos/:id} : Partial updates given fields of an existing finanzasEgreso, field will ignore if it is null
     *
     * @param id the id of the finanzasEgresoDTO to save.
     * @param finanzasEgresoDTO the finanzasEgresoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated finanzasEgresoDTO,
     * or with status {@code 400 (Bad Request)} if the finanzasEgresoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the finanzasEgresoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the finanzasEgresoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FinanzasEgresoDTO> partialUpdateFinanzasEgreso(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FinanzasEgresoDTO finanzasEgresoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FinanzasEgreso partially : {}, {}", id, finanzasEgresoDTO);
        if (finanzasEgresoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, finanzasEgresoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!finanzasEgresoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FinanzasEgresoDTO> result = finanzasEgresoService.partialUpdate(finanzasEgresoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, finanzasEgresoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /finanzas-egresos} : get all the finanzasEgresos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of finanzasEgresos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FinanzasEgresoDTO>> getAllFinanzasEgresos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of FinanzasEgresos");
        Page<FinanzasEgresoDTO> page = finanzasEgresoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /finanzas-egresos/:id} : get the "id" finanzasEgreso.
     *
     * @param id the id of the finanzasEgresoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the finanzasEgresoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FinanzasEgresoDTO> getFinanzasEgreso(@PathVariable("id") Long id) {
        log.debug("REST request to get FinanzasEgreso : {}", id);
        Optional<FinanzasEgresoDTO> finanzasEgresoDTO = finanzasEgresoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(finanzasEgresoDTO);
    }

    /**
     * {@code DELETE  /finanzas-egresos/:id} : delete the "id" finanzasEgreso.
     *
     * @param id the id of the finanzasEgresoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinanzasEgreso(@PathVariable("id") Long id) {
        log.debug("REST request to delete FinanzasEgreso : {}", id);
        finanzasEgresoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
