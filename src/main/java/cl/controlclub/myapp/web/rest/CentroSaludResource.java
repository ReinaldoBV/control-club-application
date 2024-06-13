package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.CentroSaludRepository;
import cl.controlclub.myapp.service.CentroSaludService;
import cl.controlclub.myapp.service.dto.CentroSaludDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.CentroSalud}.
 */
@RestController
@RequestMapping("/api/centro-saluds")
public class CentroSaludResource {

    private final Logger log = LoggerFactory.getLogger(CentroSaludResource.class);

    private static final String ENTITY_NAME = "centroSalud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentroSaludService centroSaludService;

    private final CentroSaludRepository centroSaludRepository;

    public CentroSaludResource(CentroSaludService centroSaludService, CentroSaludRepository centroSaludRepository) {
        this.centroSaludService = centroSaludService;
        this.centroSaludRepository = centroSaludRepository;
    }

    /**
     * {@code POST  /centro-saluds} : Create a new centroSalud.
     *
     * @param centroSaludDTO the centroSaludDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centroSaludDTO, or with status {@code 400 (Bad Request)} if the centroSalud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CentroSaludDTO> createCentroSalud(@Valid @RequestBody CentroSaludDTO centroSaludDTO) throws URISyntaxException {
        log.debug("REST request to save CentroSalud : {}", centroSaludDTO);
        if (centroSaludDTO.getId() != null) {
            throw new BadRequestAlertException("A new centroSalud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        centroSaludDTO = centroSaludService.save(centroSaludDTO);
        return ResponseEntity.created(new URI("/api/centro-saluds/" + centroSaludDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, centroSaludDTO.getId().toString()))
            .body(centroSaludDTO);
    }

    /**
     * {@code PUT  /centro-saluds/:id} : Updates an existing centroSalud.
     *
     * @param id the id of the centroSaludDTO to save.
     * @param centroSaludDTO the centroSaludDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroSaludDTO,
     * or with status {@code 400 (Bad Request)} if the centroSaludDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centroSaludDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CentroSaludDTO> updateCentroSalud(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CentroSaludDTO centroSaludDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CentroSalud : {}, {}", id, centroSaludDTO);
        if (centroSaludDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centroSaludDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centroSaludRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        centroSaludDTO = centroSaludService.update(centroSaludDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centroSaludDTO.getId().toString()))
            .body(centroSaludDTO);
    }

    /**
     * {@code PATCH  /centro-saluds/:id} : Partial updates given fields of an existing centroSalud, field will ignore if it is null
     *
     * @param id the id of the centroSaludDTO to save.
     * @param centroSaludDTO the centroSaludDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroSaludDTO,
     * or with status {@code 400 (Bad Request)} if the centroSaludDTO is not valid,
     * or with status {@code 404 (Not Found)} if the centroSaludDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the centroSaludDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CentroSaludDTO> partialUpdateCentroSalud(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CentroSaludDTO centroSaludDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CentroSalud partially : {}, {}", id, centroSaludDTO);
        if (centroSaludDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centroSaludDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centroSaludRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CentroSaludDTO> result = centroSaludService.partialUpdate(centroSaludDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centroSaludDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /centro-saluds} : get all the centroSaluds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centroSaluds in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CentroSaludDTO>> getAllCentroSaluds(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CentroSaluds");
        Page<CentroSaludDTO> page = centroSaludService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /centro-saluds/:id} : get the "id" centroSalud.
     *
     * @param id the id of the centroSaludDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centroSaludDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CentroSaludDTO> getCentroSalud(@PathVariable("id") Long id) {
        log.debug("REST request to get CentroSalud : {}", id);
        Optional<CentroSaludDTO> centroSaludDTO = centroSaludService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centroSaludDTO);
    }

    /**
     * {@code DELETE  /centro-saluds/:id} : delete the "id" centroSalud.
     *
     * @param id the id of the centroSaludDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCentroSalud(@PathVariable("id") Long id) {
        log.debug("REST request to delete CentroSalud : {}", id);
        centroSaludService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
