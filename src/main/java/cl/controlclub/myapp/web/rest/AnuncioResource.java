package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.AnuncioRepository;
import cl.controlclub.myapp.service.AnuncioService;
import cl.controlclub.myapp.service.dto.AnuncioDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.Anuncio}.
 */
@RestController
@RequestMapping("/api/anuncios")
public class AnuncioResource {

    private final Logger log = LoggerFactory.getLogger(AnuncioResource.class);

    private static final String ENTITY_NAME = "anuncio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnuncioService anuncioService;

    private final AnuncioRepository anuncioRepository;

    public AnuncioResource(AnuncioService anuncioService, AnuncioRepository anuncioRepository) {
        this.anuncioService = anuncioService;
        this.anuncioRepository = anuncioRepository;
    }

    /**
     * {@code POST  /anuncios} : Create a new anuncio.
     *
     * @param anuncioDTO the anuncioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anuncioDTO, or with status {@code 400 (Bad Request)} if the anuncio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnuncioDTO> createAnuncio(@Valid @RequestBody AnuncioDTO anuncioDTO) throws URISyntaxException {
        log.debug("REST request to save Anuncio : {}", anuncioDTO);
        if (anuncioDTO.getId() != null) {
            throw new BadRequestAlertException("A new anuncio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anuncioDTO = anuncioService.save(anuncioDTO);
        return ResponseEntity.created(new URI("/api/anuncios/" + anuncioDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anuncioDTO.getId().toString()))
            .body(anuncioDTO);
    }

    /**
     * {@code PUT  /anuncios/:id} : Updates an existing anuncio.
     *
     * @param id the id of the anuncioDTO to save.
     * @param anuncioDTO the anuncioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anuncioDTO,
     * or with status {@code 400 (Bad Request)} if the anuncioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anuncioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnuncioDTO> updateAnuncio(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnuncioDTO anuncioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Anuncio : {}, {}", id, anuncioDTO);
        if (anuncioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anuncioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anuncioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anuncioDTO = anuncioService.update(anuncioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anuncioDTO.getId().toString()))
            .body(anuncioDTO);
    }

    /**
     * {@code PATCH  /anuncios/:id} : Partial updates given fields of an existing anuncio, field will ignore if it is null
     *
     * @param id the id of the anuncioDTO to save.
     * @param anuncioDTO the anuncioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anuncioDTO,
     * or with status {@code 400 (Bad Request)} if the anuncioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anuncioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anuncioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnuncioDTO> partialUpdateAnuncio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnuncioDTO anuncioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Anuncio partially : {}, {}", id, anuncioDTO);
        if (anuncioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anuncioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anuncioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnuncioDTO> result = anuncioService.partialUpdate(anuncioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anuncioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /anuncios} : get all the anuncios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anuncios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AnuncioDTO>> getAllAnuncios(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Anuncios");
        Page<AnuncioDTO> page = anuncioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /anuncios/:id} : get the "id" anuncio.
     *
     * @param id the id of the anuncioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anuncioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnuncioDTO> getAnuncio(@PathVariable("id") Long id) {
        log.debug("REST request to get Anuncio : {}", id);
        Optional<AnuncioDTO> anuncioDTO = anuncioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anuncioDTO);
    }

    /**
     * {@code DELETE  /anuncios/:id} : delete the "id" anuncio.
     *
     * @param id the id of the anuncioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnuncio(@PathVariable("id") Long id) {
        log.debug("REST request to delete Anuncio : {}", id);
        anuncioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
