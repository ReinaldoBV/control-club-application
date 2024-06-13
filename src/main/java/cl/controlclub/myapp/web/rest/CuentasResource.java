package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.CuentasRepository;
import cl.controlclub.myapp.service.CuentasService;
import cl.controlclub.myapp.service.dto.CuentasDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.Cuentas}.
 */
@RestController
@RequestMapping("/api/cuentas")
public class CuentasResource {

    private final Logger log = LoggerFactory.getLogger(CuentasResource.class);

    private static final String ENTITY_NAME = "cuentas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CuentasService cuentasService;

    private final CuentasRepository cuentasRepository;

    public CuentasResource(CuentasService cuentasService, CuentasRepository cuentasRepository) {
        this.cuentasService = cuentasService;
        this.cuentasRepository = cuentasRepository;
    }

    /**
     * {@code POST  /cuentas} : Create a new cuentas.
     *
     * @param cuentasDTO the cuentasDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cuentasDTO, or with status {@code 400 (Bad Request)} if the cuentas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CuentasDTO> createCuentas(@Valid @RequestBody CuentasDTO cuentasDTO) throws URISyntaxException {
        log.debug("REST request to save Cuentas : {}", cuentasDTO);
        if (cuentasDTO.getId() != null) {
            throw new BadRequestAlertException("A new cuentas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cuentasDTO = cuentasService.save(cuentasDTO);
        return ResponseEntity.created(new URI("/api/cuentas/" + cuentasDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cuentasDTO.getId().toString()))
            .body(cuentasDTO);
    }

    /**
     * {@code PUT  /cuentas/:id} : Updates an existing cuentas.
     *
     * @param id the id of the cuentasDTO to save.
     * @param cuentasDTO the cuentasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuentasDTO,
     * or with status {@code 400 (Bad Request)} if the cuentasDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cuentasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CuentasDTO> updateCuentas(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CuentasDTO cuentasDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Cuentas : {}, {}", id, cuentasDTO);
        if (cuentasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cuentasDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cuentasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cuentasDTO = cuentasService.update(cuentasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuentasDTO.getId().toString()))
            .body(cuentasDTO);
    }

    /**
     * {@code PATCH  /cuentas/:id} : Partial updates given fields of an existing cuentas, field will ignore if it is null
     *
     * @param id the id of the cuentasDTO to save.
     * @param cuentasDTO the cuentasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuentasDTO,
     * or with status {@code 400 (Bad Request)} if the cuentasDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cuentasDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cuentasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CuentasDTO> partialUpdateCuentas(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CuentasDTO cuentasDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cuentas partially : {}, {}", id, cuentasDTO);
        if (cuentasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cuentasDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cuentasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CuentasDTO> result = cuentasService.partialUpdate(cuentasDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuentasDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cuentas} : get all the cuentas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cuentas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CuentasDTO>> getAllCuentas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Cuentas");
        Page<CuentasDTO> page = cuentasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cuentas/:id} : get the "id" cuentas.
     *
     * @param id the id of the cuentasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cuentasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CuentasDTO> getCuentas(@PathVariable("id") Long id) {
        log.debug("REST request to get Cuentas : {}", id);
        Optional<CuentasDTO> cuentasDTO = cuentasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cuentasDTO);
    }

    /**
     * {@code DELETE  /cuentas/:id} : delete the "id" cuentas.
     *
     * @param id the id of the cuentasDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuentas(@PathVariable("id") Long id) {
        log.debug("REST request to delete Cuentas : {}", id);
        cuentasService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
