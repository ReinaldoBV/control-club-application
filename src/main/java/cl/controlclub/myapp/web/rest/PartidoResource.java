package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.PartidoRepository;
import cl.controlclub.myapp.service.PartidoService;
import cl.controlclub.myapp.service.dto.PartidoDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.Partido}.
 */
@RestController
@RequestMapping("/api/partidos")
public class PartidoResource {

    private final Logger log = LoggerFactory.getLogger(PartidoResource.class);

    private static final String ENTITY_NAME = "partido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartidoService partidoService;

    private final PartidoRepository partidoRepository;

    public PartidoResource(PartidoService partidoService, PartidoRepository partidoRepository) {
        this.partidoService = partidoService;
        this.partidoRepository = partidoRepository;
    }

    /**
     * {@code POST  /partidos} : Create a new partido.
     *
     * @param partidoDTO the partidoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partidoDTO, or with status {@code 400 (Bad Request)} if the partido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PartidoDTO> createPartido(@Valid @RequestBody PartidoDTO partidoDTO) throws URISyntaxException {
        log.debug("REST request to save Partido : {}", partidoDTO);
        if (partidoDTO.getId() != null) {
            throw new BadRequestAlertException("A new partido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        partidoDTO = partidoService.save(partidoDTO);
        return ResponseEntity.created(new URI("/api/partidos/" + partidoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, partidoDTO.getId().toString()))
            .body(partidoDTO);
    }

    /**
     * {@code PUT  /partidos/:id} : Updates an existing partido.
     *
     * @param id the id of the partidoDTO to save.
     * @param partidoDTO the partidoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partidoDTO,
     * or with status {@code 400 (Bad Request)} if the partidoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partidoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PartidoDTO> updatePartido(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PartidoDTO partidoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Partido : {}, {}", id, partidoDTO);
        if (partidoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partidoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        partidoDTO = partidoService.update(partidoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partidoDTO.getId().toString()))
            .body(partidoDTO);
    }

    /**
     * {@code PATCH  /partidos/:id} : Partial updates given fields of an existing partido, field will ignore if it is null
     *
     * @param id the id of the partidoDTO to save.
     * @param partidoDTO the partidoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partidoDTO,
     * or with status {@code 400 (Bad Request)} if the partidoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the partidoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the partidoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PartidoDTO> partialUpdatePartido(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PartidoDTO partidoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Partido partially : {}, {}", id, partidoDTO);
        if (partidoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, partidoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!partidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PartidoDTO> result = partidoService.partialUpdate(partidoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partidoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /partidos} : get all the partidos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partidos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PartidoDTO>> getAllPartidos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Partidos");
        Page<PartidoDTO> page = partidoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /partidos/:id} : get the "id" partido.
     *
     * @param id the id of the partidoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partidoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PartidoDTO> getPartido(@PathVariable("id") Long id) {
        log.debug("REST request to get Partido : {}", id);
        Optional<PartidoDTO> partidoDTO = partidoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partidoDTO);
    }

    /**
     * {@code DELETE  /partidos/:id} : delete the "id" partido.
     *
     * @param id the id of the partidoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartido(@PathVariable("id") Long id) {
        log.debug("REST request to delete Partido : {}", id);
        partidoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
