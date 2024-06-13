package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.JugadorRepository;
import cl.controlclub.myapp.service.JugadorService;
import cl.controlclub.myapp.service.dto.JugadorDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cl.controlclub.myapp.domain.Jugador}.
 */
@RestController
@RequestMapping("/api/jugadors")
public class JugadorResource {

    private final Logger log = LoggerFactory.getLogger(JugadorResource.class);

    private static final String ENTITY_NAME = "jugador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JugadorService jugadorService;

    private final JugadorRepository jugadorRepository;

    public JugadorResource(JugadorService jugadorService, JugadorRepository jugadorRepository) {
        this.jugadorService = jugadorService;
        this.jugadorRepository = jugadorRepository;
    }

    /**
     * {@code POST  /jugadors} : Create a new jugador.
     *
     * @param jugadorDTO the jugadorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jugadorDTO, or with status {@code 400 (Bad Request)} if the jugador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<JugadorDTO> createJugador(@Valid @RequestBody JugadorDTO jugadorDTO) throws URISyntaxException {
        log.debug("REST request to save Jugador : {}", jugadorDTO);
        if (jugadorDTO.getId() != null) {
            throw new BadRequestAlertException("A new jugador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        jugadorDTO = jugadorService.save(jugadorDTO);
        return ResponseEntity.created(new URI("/api/jugadors/" + jugadorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, jugadorDTO.getId().toString()))
            .body(jugadorDTO);
    }

    /**
     * {@code PUT  /jugadors/:id} : Updates an existing jugador.
     *
     * @param id the id of the jugadorDTO to save.
     * @param jugadorDTO the jugadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jugadorDTO,
     * or with status {@code 400 (Bad Request)} if the jugadorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jugadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<JugadorDTO> updateJugador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody JugadorDTO jugadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Jugador : {}, {}", id, jugadorDTO);
        if (jugadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jugadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jugadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        jugadorDTO = jugadorService.update(jugadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jugadorDTO.getId().toString()))
            .body(jugadorDTO);
    }

    /**
     * {@code PATCH  /jugadors/:id} : Partial updates given fields of an existing jugador, field will ignore if it is null
     *
     * @param id the id of the jugadorDTO to save.
     * @param jugadorDTO the jugadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jugadorDTO,
     * or with status {@code 400 (Bad Request)} if the jugadorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the jugadorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the jugadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JugadorDTO> partialUpdateJugador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody JugadorDTO jugadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Jugador partially : {}, {}", id, jugadorDTO);
        if (jugadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jugadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jugadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JugadorDTO> result = jugadorService.partialUpdate(jugadorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jugadorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /jugadors} : get all the jugadors.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jugadors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<JugadorDTO>> getAllJugadors(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("usuario-is-null".equals(filter)) {
            log.debug("REST request to get all Jugadors where usuario is null");
            return new ResponseEntity<>(jugadorService.findAllWhereUsuarioIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Jugadors");
        Page<JugadorDTO> page = jugadorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jugadors/:id} : get the "id" jugador.
     *
     * @param id the id of the jugadorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jugadorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> getJugador(@PathVariable("id") Long id) {
        log.debug("REST request to get Jugador : {}", id);
        Optional<JugadorDTO> jugadorDTO = jugadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jugadorDTO);
    }

    /**
     * {@code DELETE  /jugadors/:id} : delete the "id" jugador.
     *
     * @param id the id of the jugadorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJugador(@PathVariable("id") Long id) {
        log.debug("REST request to delete Jugador : {}", id);
        jugadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
