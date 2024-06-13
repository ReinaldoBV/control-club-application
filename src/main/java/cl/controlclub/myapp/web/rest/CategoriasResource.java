package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.CategoriasRepository;
import cl.controlclub.myapp.service.CategoriasService;
import cl.controlclub.myapp.service.dto.CategoriasDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.Categorias}.
 */
@RestController
@RequestMapping("/api/categorias")
public class CategoriasResource {

    private final Logger log = LoggerFactory.getLogger(CategoriasResource.class);

    private static final String ENTITY_NAME = "categorias";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriasService categoriasService;

    private final CategoriasRepository categoriasRepository;

    public CategoriasResource(CategoriasService categoriasService, CategoriasRepository categoriasRepository) {
        this.categoriasService = categoriasService;
        this.categoriasRepository = categoriasRepository;
    }

    /**
     * {@code POST  /categorias} : Create a new categorias.
     *
     * @param categoriasDTO the categoriasDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriasDTO, or with status {@code 400 (Bad Request)} if the categorias has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CategoriasDTO> createCategorias(@Valid @RequestBody CategoriasDTO categoriasDTO) throws URISyntaxException {
        log.debug("REST request to save Categorias : {}", categoriasDTO);
        if (categoriasDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorias cannot already have an ID", ENTITY_NAME, "idexists");
        }
        categoriasDTO = categoriasService.save(categoriasDTO);
        return ResponseEntity.created(new URI("/api/categorias/" + categoriasDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, categoriasDTO.getId().toString()))
            .body(categoriasDTO);
    }

    /**
     * {@code PUT  /categorias/:id} : Updates an existing categorias.
     *
     * @param id the id of the categoriasDTO to save.
     * @param categoriasDTO the categoriasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriasDTO,
     * or with status {@code 400 (Bad Request)} if the categoriasDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoriasDTO> updateCategorias(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CategoriasDTO categoriasDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Categorias : {}, {}", id, categoriasDTO);
        if (categoriasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriasDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        categoriasDTO = categoriasService.update(categoriasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriasDTO.getId().toString()))
            .body(categoriasDTO);
    }

    /**
     * {@code PATCH  /categorias/:id} : Partial updates given fields of an existing categorias, field will ignore if it is null
     *
     * @param id the id of the categoriasDTO to save.
     * @param categoriasDTO the categoriasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriasDTO,
     * or with status {@code 400 (Bad Request)} if the categoriasDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categoriasDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoriasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategoriasDTO> partialUpdateCategorias(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CategoriasDTO categoriasDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Categorias partially : {}, {}", id, categoriasDTO);
        if (categoriasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoriasDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoriasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoriasDTO> result = categoriasService.partialUpdate(categoriasDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriasDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categorias} : get all the categorias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CategoriasDTO>> getAllCategorias(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Categorias");
        Page<CategoriasDTO> page = categoriasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categorias/:id} : get the "id" categorias.
     *
     * @param id the id of the categoriasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriasDTO> getCategorias(@PathVariable("id") Long id) {
        log.debug("REST request to get Categorias : {}", id);
        Optional<CategoriasDTO> categoriasDTO = categoriasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriasDTO);
    }

    /**
     * {@code DELETE  /categorias/:id} : delete the "id" categorias.
     *
     * @param id the id of the categoriasDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorias(@PathVariable("id") Long id) {
        log.debug("REST request to delete Categorias : {}", id);
        categoriasService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
