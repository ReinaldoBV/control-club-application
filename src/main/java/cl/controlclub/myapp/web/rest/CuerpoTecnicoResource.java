package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.CuerpoTecnicoRepository;
import cl.controlclub.myapp.service.CuerpoTecnicoService;
import cl.controlclub.myapp.service.dto.CuerpoTecnicoDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.CuerpoTecnico}.
 */
@RestController
@RequestMapping("/api/cuerpo-tecnicos")
public class CuerpoTecnicoResource {

    private final Logger log = LoggerFactory.getLogger(CuerpoTecnicoResource.class);

    private static final String ENTITY_NAME = "cuerpoTecnico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CuerpoTecnicoService cuerpoTecnicoService;

    private final CuerpoTecnicoRepository cuerpoTecnicoRepository;

    public CuerpoTecnicoResource(CuerpoTecnicoService cuerpoTecnicoService, CuerpoTecnicoRepository cuerpoTecnicoRepository) {
        this.cuerpoTecnicoService = cuerpoTecnicoService;
        this.cuerpoTecnicoRepository = cuerpoTecnicoRepository;
    }

    /**
     * {@code POST  /cuerpo-tecnicos} : Create a new cuerpoTecnico.
     *
     * @param cuerpoTecnicoDTO the cuerpoTecnicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cuerpoTecnicoDTO, or with status {@code 400 (Bad Request)} if the cuerpoTecnico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CuerpoTecnicoDTO> createCuerpoTecnico(@Valid @RequestBody CuerpoTecnicoDTO cuerpoTecnicoDTO)
        throws URISyntaxException {
        log.debug("REST request to save CuerpoTecnico : {}", cuerpoTecnicoDTO);
        if (cuerpoTecnicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new cuerpoTecnico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cuerpoTecnicoDTO = cuerpoTecnicoService.save(cuerpoTecnicoDTO);
        return ResponseEntity.created(new URI("/api/cuerpo-tecnicos/" + cuerpoTecnicoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cuerpoTecnicoDTO.getId().toString()))
            .body(cuerpoTecnicoDTO);
    }

    /**
     * {@code PUT  /cuerpo-tecnicos/:id} : Updates an existing cuerpoTecnico.
     *
     * @param id the id of the cuerpoTecnicoDTO to save.
     * @param cuerpoTecnicoDTO the cuerpoTecnicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuerpoTecnicoDTO,
     * or with status {@code 400 (Bad Request)} if the cuerpoTecnicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cuerpoTecnicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CuerpoTecnicoDTO> updateCuerpoTecnico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CuerpoTecnicoDTO cuerpoTecnicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CuerpoTecnico : {}, {}", id, cuerpoTecnicoDTO);
        if (cuerpoTecnicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cuerpoTecnicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cuerpoTecnicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cuerpoTecnicoDTO = cuerpoTecnicoService.update(cuerpoTecnicoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuerpoTecnicoDTO.getId().toString()))
            .body(cuerpoTecnicoDTO);
    }

    /**
     * {@code PATCH  /cuerpo-tecnicos/:id} : Partial updates given fields of an existing cuerpoTecnico, field will ignore if it is null
     *
     * @param id the id of the cuerpoTecnicoDTO to save.
     * @param cuerpoTecnicoDTO the cuerpoTecnicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuerpoTecnicoDTO,
     * or with status {@code 400 (Bad Request)} if the cuerpoTecnicoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cuerpoTecnicoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cuerpoTecnicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CuerpoTecnicoDTO> partialUpdateCuerpoTecnico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CuerpoTecnicoDTO cuerpoTecnicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CuerpoTecnico partially : {}, {}", id, cuerpoTecnicoDTO);
        if (cuerpoTecnicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cuerpoTecnicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cuerpoTecnicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CuerpoTecnicoDTO> result = cuerpoTecnicoService.partialUpdate(cuerpoTecnicoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuerpoTecnicoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cuerpo-tecnicos} : get all the cuerpoTecnicos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cuerpoTecnicos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CuerpoTecnicoDTO>> getAllCuerpoTecnicos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CuerpoTecnicos");
        Page<CuerpoTecnicoDTO> page = cuerpoTecnicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cuerpo-tecnicos/:id} : get the "id" cuerpoTecnico.
     *
     * @param id the id of the cuerpoTecnicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cuerpoTecnicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CuerpoTecnicoDTO> getCuerpoTecnico(@PathVariable("id") Long id) {
        log.debug("REST request to get CuerpoTecnico : {}", id);
        Optional<CuerpoTecnicoDTO> cuerpoTecnicoDTO = cuerpoTecnicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cuerpoTecnicoDTO);
    }

    /**
     * {@code DELETE  /cuerpo-tecnicos/:id} : delete the "id" cuerpoTecnico.
     *
     * @param id the id of the cuerpoTecnicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuerpoTecnico(@PathVariable("id") Long id) {
        log.debug("REST request to delete CuerpoTecnico : {}", id);
        cuerpoTecnicoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
