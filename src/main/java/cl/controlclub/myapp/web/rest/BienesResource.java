package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.BienesRepository;
import cl.controlclub.myapp.service.BienesService;
import cl.controlclub.myapp.service.dto.BienesDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.Bienes}.
 */
@RestController
@RequestMapping("/api/bienes")
public class BienesResource {

    private final Logger log = LoggerFactory.getLogger(BienesResource.class);

    private static final String ENTITY_NAME = "bienes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BienesService bienesService;

    private final BienesRepository bienesRepository;

    public BienesResource(BienesService bienesService, BienesRepository bienesRepository) {
        this.bienesService = bienesService;
        this.bienesRepository = bienesRepository;
    }

    /**
     * {@code POST  /bienes} : Create a new bienes.
     *
     * @param bienesDTO the bienesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bienesDTO, or with status {@code 400 (Bad Request)} if the bienes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BienesDTO> createBienes(@Valid @RequestBody BienesDTO bienesDTO) throws URISyntaxException {
        log.debug("REST request to save Bienes : {}", bienesDTO);
        if (bienesDTO.getId() != null) {
            throw new BadRequestAlertException("A new bienes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bienesDTO = bienesService.save(bienesDTO);
        return ResponseEntity.created(new URI("/api/bienes/" + bienesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bienesDTO.getId().toString()))
            .body(bienesDTO);
    }

    /**
     * {@code PUT  /bienes/:id} : Updates an existing bienes.
     *
     * @param id the id of the bienesDTO to save.
     * @param bienesDTO the bienesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bienesDTO,
     * or with status {@code 400 (Bad Request)} if the bienesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bienesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BienesDTO> updateBienes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BienesDTO bienesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Bienes : {}, {}", id, bienesDTO);
        if (bienesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bienesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bienesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bienesDTO = bienesService.update(bienesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bienesDTO.getId().toString()))
            .body(bienesDTO);
    }

    /**
     * {@code PATCH  /bienes/:id} : Partial updates given fields of an existing bienes, field will ignore if it is null
     *
     * @param id the id of the bienesDTO to save.
     * @param bienesDTO the bienesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bienesDTO,
     * or with status {@code 400 (Bad Request)} if the bienesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bienesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bienesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BienesDTO> partialUpdateBienes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BienesDTO bienesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bienes partially : {}, {}", id, bienesDTO);
        if (bienesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bienesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bienesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BienesDTO> result = bienesService.partialUpdate(bienesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bienesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bienes} : get all the bienes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bienes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BienesDTO>> getAllBienes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Bienes");
        Page<BienesDTO> page = bienesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bienes/:id} : get the "id" bienes.
     *
     * @param id the id of the bienesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bienesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BienesDTO> getBienes(@PathVariable("id") Long id) {
        log.debug("REST request to get Bienes : {}", id);
        Optional<BienesDTO> bienesDTO = bienesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bienesDTO);
    }

    /**
     * {@code DELETE  /bienes/:id} : delete the "id" bienes.
     *
     * @param id the id of the bienesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBienes(@PathVariable("id") Long id) {
        log.debug("REST request to delete Bienes : {}", id);
        bienesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
