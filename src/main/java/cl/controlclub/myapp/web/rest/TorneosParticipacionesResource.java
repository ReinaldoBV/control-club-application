package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.TorneosParticipacionesRepository;
import cl.controlclub.myapp.service.TorneosParticipacionesService;
import cl.controlclub.myapp.service.dto.TorneosParticipacionesDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.TorneosParticipaciones}.
 */
@RestController
@RequestMapping("/api/torneos-participaciones")
public class TorneosParticipacionesResource {

    private final Logger log = LoggerFactory.getLogger(TorneosParticipacionesResource.class);

    private static final String ENTITY_NAME = "torneosParticipaciones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TorneosParticipacionesService torneosParticipacionesService;

    private final TorneosParticipacionesRepository torneosParticipacionesRepository;

    public TorneosParticipacionesResource(
        TorneosParticipacionesService torneosParticipacionesService,
        TorneosParticipacionesRepository torneosParticipacionesRepository
    ) {
        this.torneosParticipacionesService = torneosParticipacionesService;
        this.torneosParticipacionesRepository = torneosParticipacionesRepository;
    }

    /**
     * {@code POST  /torneos-participaciones} : Create a new torneosParticipaciones.
     *
     * @param torneosParticipacionesDTO the torneosParticipacionesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new torneosParticipacionesDTO, or with status {@code 400 (Bad Request)} if the torneosParticipaciones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TorneosParticipacionesDTO> createTorneosParticipaciones(
        @Valid @RequestBody TorneosParticipacionesDTO torneosParticipacionesDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TorneosParticipaciones : {}", torneosParticipacionesDTO);
        if (torneosParticipacionesDTO.getId() != null) {
            throw new BadRequestAlertException("A new torneosParticipaciones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        torneosParticipacionesDTO = torneosParticipacionesService.save(torneosParticipacionesDTO);
        return ResponseEntity.created(new URI("/api/torneos-participaciones/" + torneosParticipacionesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, torneosParticipacionesDTO.getId().toString()))
            .body(torneosParticipacionesDTO);
    }

    /**
     * {@code PUT  /torneos-participaciones/:id} : Updates an existing torneosParticipaciones.
     *
     * @param id the id of the torneosParticipacionesDTO to save.
     * @param torneosParticipacionesDTO the torneosParticipacionesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated torneosParticipacionesDTO,
     * or with status {@code 400 (Bad Request)} if the torneosParticipacionesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the torneosParticipacionesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TorneosParticipacionesDTO> updateTorneosParticipaciones(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TorneosParticipacionesDTO torneosParticipacionesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TorneosParticipaciones : {}, {}", id, torneosParticipacionesDTO);
        if (torneosParticipacionesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, torneosParticipacionesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!torneosParticipacionesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        torneosParticipacionesDTO = torneosParticipacionesService.update(torneosParticipacionesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, torneosParticipacionesDTO.getId().toString()))
            .body(torneosParticipacionesDTO);
    }

    /**
     * {@code PATCH  /torneos-participaciones/:id} : Partial updates given fields of an existing torneosParticipaciones, field will ignore if it is null
     *
     * @param id the id of the torneosParticipacionesDTO to save.
     * @param torneosParticipacionesDTO the torneosParticipacionesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated torneosParticipacionesDTO,
     * or with status {@code 400 (Bad Request)} if the torneosParticipacionesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the torneosParticipacionesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the torneosParticipacionesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TorneosParticipacionesDTO> partialUpdateTorneosParticipaciones(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TorneosParticipacionesDTO torneosParticipacionesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TorneosParticipaciones partially : {}, {}", id, torneosParticipacionesDTO);
        if (torneosParticipacionesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, torneosParticipacionesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!torneosParticipacionesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TorneosParticipacionesDTO> result = torneosParticipacionesService.partialUpdate(torneosParticipacionesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, torneosParticipacionesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /torneos-participaciones} : get all the torneosParticipaciones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of torneosParticipaciones in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TorneosParticipacionesDTO>> getAllTorneosParticipaciones(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TorneosParticipaciones");
        Page<TorneosParticipacionesDTO> page = torneosParticipacionesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /torneos-participaciones/:id} : get the "id" torneosParticipaciones.
     *
     * @param id the id of the torneosParticipacionesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the torneosParticipacionesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TorneosParticipacionesDTO> getTorneosParticipaciones(@PathVariable("id") Long id) {
        log.debug("REST request to get TorneosParticipaciones : {}", id);
        Optional<TorneosParticipacionesDTO> torneosParticipacionesDTO = torneosParticipacionesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(torneosParticipacionesDTO);
    }

    /**
     * {@code DELETE  /torneos-participaciones/:id} : delete the "id" torneosParticipaciones.
     *
     * @param id the id of the torneosParticipacionesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTorneosParticipaciones(@PathVariable("id") Long id) {
        log.debug("REST request to delete TorneosParticipaciones : {}", id);
        torneosParticipacionesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
