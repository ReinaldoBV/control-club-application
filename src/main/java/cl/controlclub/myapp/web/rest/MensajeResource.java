package cl.controlclub.myapp.web.rest;

import cl.controlclub.myapp.repository.MensajeRepository;
import cl.controlclub.myapp.service.MensajeService;
import cl.controlclub.myapp.service.dto.MensajeDTO;
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
 * REST controller for managing {@link cl.controlclub.myapp.domain.Mensaje}.
 */
@RestController
@RequestMapping("/api/mensajes")
public class MensajeResource {

    private final Logger log = LoggerFactory.getLogger(MensajeResource.class);

    private static final String ENTITY_NAME = "mensaje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MensajeService mensajeService;

    private final MensajeRepository mensajeRepository;

    public MensajeResource(MensajeService mensajeService, MensajeRepository mensajeRepository) {
        this.mensajeService = mensajeService;
        this.mensajeRepository = mensajeRepository;
    }

    /**
     * {@code POST  /mensajes} : Create a new mensaje.
     *
     * @param mensajeDTO the mensajeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mensajeDTO, or with status {@code 400 (Bad Request)} if the mensaje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MensajeDTO> createMensaje(@Valid @RequestBody MensajeDTO mensajeDTO) throws URISyntaxException {
        log.debug("REST request to save Mensaje : {}", mensajeDTO);
        if (mensajeDTO.getId() != null) {
            throw new BadRequestAlertException("A new mensaje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mensajeDTO = mensajeService.save(mensajeDTO);
        return ResponseEntity.created(new URI("/api/mensajes/" + mensajeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, mensajeDTO.getId().toString()))
            .body(mensajeDTO);
    }

    /**
     * {@code PUT  /mensajes/:id} : Updates an existing mensaje.
     *
     * @param id the id of the mensajeDTO to save.
     * @param mensajeDTO the mensajeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mensajeDTO,
     * or with status {@code 400 (Bad Request)} if the mensajeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mensajeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO> updateMensaje(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MensajeDTO mensajeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Mensaje : {}, {}", id, mensajeDTO);
        if (mensajeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mensajeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mensajeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        mensajeDTO = mensajeService.update(mensajeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mensajeDTO.getId().toString()))
            .body(mensajeDTO);
    }

    /**
     * {@code PATCH  /mensajes/:id} : Partial updates given fields of an existing mensaje, field will ignore if it is null
     *
     * @param id the id of the mensajeDTO to save.
     * @param mensajeDTO the mensajeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mensajeDTO,
     * or with status {@code 400 (Bad Request)} if the mensajeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mensajeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mensajeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MensajeDTO> partialUpdateMensaje(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MensajeDTO mensajeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mensaje partially : {}, {}", id, mensajeDTO);
        if (mensajeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mensajeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mensajeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MensajeDTO> result = mensajeService.partialUpdate(mensajeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mensajeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mensajes} : get all the mensajes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mensajes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MensajeDTO>> getAllMensajes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Mensajes");
        Page<MensajeDTO> page = mensajeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mensajes/:id} : get the "id" mensaje.
     *
     * @param id the id of the mensajeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mensajeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO> getMensaje(@PathVariable("id") Long id) {
        log.debug("REST request to get Mensaje : {}", id);
        Optional<MensajeDTO> mensajeDTO = mensajeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mensajeDTO);
    }

    /**
     * {@code DELETE  /mensajes/:id} : delete the "id" mensaje.
     *
     * @param id the id of the mensajeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMensaje(@PathVariable("id") Long id) {
        log.debug("REST request to delete Mensaje : {}", id);
        mensajeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
