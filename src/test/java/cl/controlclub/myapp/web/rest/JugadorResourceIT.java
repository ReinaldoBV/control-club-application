package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.JugadorAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.domain.enumeration.Nacionalidad;
import cl.controlclub.myapp.domain.enumeration.TipoIdentificacion;
import cl.controlclub.myapp.repository.JugadorRepository;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import cl.controlclub.myapp.service.mapper.JugadorMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link JugadorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JugadorResourceIT {

    private static final Long DEFAULT_ID_JUGADOR = 1L;
    private static final Long UPDATED_ID_JUGADOR = 2L;

    private static final Long DEFAULT_NRO_IDENTIFICACION = 1L;
    private static final Long UPDATED_NRO_IDENTIFICACION = 2L;

    private static final TipoIdentificacion DEFAULT_TIPO_IDENTIFICACION = TipoIdentificacion.RUT;
    private static final TipoIdentificacion UPDATED_TIPO_IDENTIFICACION = TipoIdentificacion.PASAPORTE;

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final Nacionalidad DEFAULT_NACIONALIDAD = Nacionalidad.CHILENA;
    private static final Nacionalidad UPDATED_NACIONALIDAD = Nacionalidad.VENEZOLANA;

    private static final Integer DEFAULT_EDAD = 1;
    private static final Integer UPDATED_EDAD = 2;

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMERO_CAMISA = 1;
    private static final Integer UPDATED_NUMERO_CAMISA = 2;

    private static final String DEFAULT_CONTACTO_EMERGENCIA = "AAAAAAAAAA";
    private static final String UPDATED_CONTACTO_EMERGENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CALLE_AVENIDA_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_CALLE_AVENIDA_DIRECCION = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMERO_DIRECCION = 1L;
    private static final Long UPDATED_NUMERO_DIRECCION = 2L;

    private static final Long DEFAULT_NUMERO_PERSONAL = 1L;
    private static final Long UPDATED_NUMERO_PERSONAL = 2L;

    private static final byte[] DEFAULT_IMAGEN_JUGADOR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN_JUGADOR = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_JUGADOR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_JUGADOR_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_DOCUMENTO_IDENTIFICACION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENTO_IDENTIFICACION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENTO_IDENTIFICACION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENTO_IDENTIFICACION_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/jugadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private JugadorMapper jugadorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJugadorMockMvc;

    private Jugador jugador;

    private Jugador insertedJugador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jugador createEntity(EntityManager em) {
        Jugador jugador = new Jugador()
            .idJugador(DEFAULT_ID_JUGADOR)
            .nroIdentificacion(DEFAULT_NRO_IDENTIFICACION)
            .tipoIdentificacion(DEFAULT_TIPO_IDENTIFICACION)
            .nombres(DEFAULT_NOMBRES)
            .apellidos(DEFAULT_APELLIDOS)
            .nacionalidad(DEFAULT_NACIONALIDAD)
            .edad(DEFAULT_EDAD)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .numeroCamisa(DEFAULT_NUMERO_CAMISA)
            .contactoEmergencia(DEFAULT_CONTACTO_EMERGENCIA)
            .calleAvenidaDireccion(DEFAULT_CALLE_AVENIDA_DIRECCION)
            .numeroDireccion(DEFAULT_NUMERO_DIRECCION)
            .numeroPersonal(DEFAULT_NUMERO_PERSONAL)
            .imagenJugador(DEFAULT_IMAGEN_JUGADOR)
            .imagenJugadorContentType(DEFAULT_IMAGEN_JUGADOR_CONTENT_TYPE)
            .documentoIdentificacion(DEFAULT_DOCUMENTO_IDENTIFICACION)
            .documentoIdentificacionContentType(DEFAULT_DOCUMENTO_IDENTIFICACION_CONTENT_TYPE);
        return jugador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jugador createUpdatedEntity(EntityManager em) {
        Jugador jugador = new Jugador()
            .idJugador(UPDATED_ID_JUGADOR)
            .nroIdentificacion(UPDATED_NRO_IDENTIFICACION)
            .tipoIdentificacion(UPDATED_TIPO_IDENTIFICACION)
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .nacionalidad(UPDATED_NACIONALIDAD)
            .edad(UPDATED_EDAD)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .numeroCamisa(UPDATED_NUMERO_CAMISA)
            .contactoEmergencia(UPDATED_CONTACTO_EMERGENCIA)
            .calleAvenidaDireccion(UPDATED_CALLE_AVENIDA_DIRECCION)
            .numeroDireccion(UPDATED_NUMERO_DIRECCION)
            .numeroPersonal(UPDATED_NUMERO_PERSONAL)
            .imagenJugador(UPDATED_IMAGEN_JUGADOR)
            .imagenJugadorContentType(UPDATED_IMAGEN_JUGADOR_CONTENT_TYPE)
            .documentoIdentificacion(UPDATED_DOCUMENTO_IDENTIFICACION)
            .documentoIdentificacionContentType(UPDATED_DOCUMENTO_IDENTIFICACION_CONTENT_TYPE);
        return jugador;
    }

    @BeforeEach
    public void initTest() {
        jugador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedJugador != null) {
            jugadorRepository.delete(insertedJugador);
            insertedJugador = null;
        }
    }

    @Test
    @Transactional
    void createJugador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Jugador
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);
        var returnedJugadorDTO = om.readValue(
            restJugadorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            JugadorDTO.class
        );

        // Validate the Jugador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedJugador = jugadorMapper.toEntity(returnedJugadorDTO);
        assertJugadorUpdatableFieldsEquals(returnedJugador, getPersistedJugador(returnedJugador));

        insertedJugador = returnedJugador;
    }

    @Test
    @Transactional
    void createJugadorWithExistingId() throws Exception {
        // Create the Jugador with an existing ID
        jugador.setId(1L);
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Jugador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdJugadorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setIdJugador(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNroIdentificacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setNroIdentificacion(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoIdentificacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setTipoIdentificacion(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombresIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setNombres(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setApellidos(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNacionalidadIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setNacionalidad(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEdadIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setEdad(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaNacimientoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setFechaNacimiento(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroCamisaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setNumeroCamisa(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContactoEmergenciaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setContactoEmergencia(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCalleAvenidaDireccionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setCalleAvenidaDireccion(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroDireccionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setNumeroDireccion(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumeroPersonalIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jugador.setNumeroPersonal(null);

        // Create the Jugador, which fails.
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        restJugadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllJugadors() throws Exception {
        // Initialize the database
        insertedJugador = jugadorRepository.saveAndFlush(jugador);

        // Get all the jugadorList
        restJugadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jugador.getId().intValue())))
            .andExpect(jsonPath("$.[*].idJugador").value(hasItem(DEFAULT_ID_JUGADOR.intValue())))
            .andExpect(jsonPath("$.[*].nroIdentificacion").value(hasItem(DEFAULT_NRO_IDENTIFICACION.intValue())))
            .andExpect(jsonPath("$.[*].tipoIdentificacion").value(hasItem(DEFAULT_TIPO_IDENTIFICACION.toString())))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].nacionalidad").value(hasItem(DEFAULT_NACIONALIDAD.toString())))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].numeroCamisa").value(hasItem(DEFAULT_NUMERO_CAMISA)))
            .andExpect(jsonPath("$.[*].contactoEmergencia").value(hasItem(DEFAULT_CONTACTO_EMERGENCIA)))
            .andExpect(jsonPath("$.[*].calleAvenidaDireccion").value(hasItem(DEFAULT_CALLE_AVENIDA_DIRECCION)))
            .andExpect(jsonPath("$.[*].numeroDireccion").value(hasItem(DEFAULT_NUMERO_DIRECCION.intValue())))
            .andExpect(jsonPath("$.[*].numeroPersonal").value(hasItem(DEFAULT_NUMERO_PERSONAL.intValue())))
            .andExpect(jsonPath("$.[*].imagenJugadorContentType").value(hasItem(DEFAULT_IMAGEN_JUGADOR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagenJugador").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_JUGADOR))))
            .andExpect(jsonPath("$.[*].documentoIdentificacionContentType").value(hasItem(DEFAULT_DOCUMENTO_IDENTIFICACION_CONTENT_TYPE)))
            .andExpect(
                jsonPath("$.[*].documentoIdentificacion").value(
                    hasItem(Base64.getEncoder().encodeToString(DEFAULT_DOCUMENTO_IDENTIFICACION))
                )
            );
    }

    @Test
    @Transactional
    void getJugador() throws Exception {
        // Initialize the database
        insertedJugador = jugadorRepository.saveAndFlush(jugador);

        // Get the jugador
        restJugadorMockMvc
            .perform(get(ENTITY_API_URL_ID, jugador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jugador.getId().intValue()))
            .andExpect(jsonPath("$.idJugador").value(DEFAULT_ID_JUGADOR.intValue()))
            .andExpect(jsonPath("$.nroIdentificacion").value(DEFAULT_NRO_IDENTIFICACION.intValue()))
            .andExpect(jsonPath("$.tipoIdentificacion").value(DEFAULT_TIPO_IDENTIFICACION.toString()))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.nacionalidad").value(DEFAULT_NACIONALIDAD.toString()))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.numeroCamisa").value(DEFAULT_NUMERO_CAMISA))
            .andExpect(jsonPath("$.contactoEmergencia").value(DEFAULT_CONTACTO_EMERGENCIA))
            .andExpect(jsonPath("$.calleAvenidaDireccion").value(DEFAULT_CALLE_AVENIDA_DIRECCION))
            .andExpect(jsonPath("$.numeroDireccion").value(DEFAULT_NUMERO_DIRECCION.intValue()))
            .andExpect(jsonPath("$.numeroPersonal").value(DEFAULT_NUMERO_PERSONAL.intValue()))
            .andExpect(jsonPath("$.imagenJugadorContentType").value(DEFAULT_IMAGEN_JUGADOR_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagenJugador").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGEN_JUGADOR)))
            .andExpect(jsonPath("$.documentoIdentificacionContentType").value(DEFAULT_DOCUMENTO_IDENTIFICACION_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentoIdentificacion").value(Base64.getEncoder().encodeToString(DEFAULT_DOCUMENTO_IDENTIFICACION)));
    }

    @Test
    @Transactional
    void getNonExistingJugador() throws Exception {
        // Get the jugador
        restJugadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJugador() throws Exception {
        // Initialize the database
        insertedJugador = jugadorRepository.saveAndFlush(jugador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jugador
        Jugador updatedJugador = jugadorRepository.findById(jugador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedJugador are not directly saved in db
        em.detach(updatedJugador);
        updatedJugador
            .idJugador(UPDATED_ID_JUGADOR)
            .nroIdentificacion(UPDATED_NRO_IDENTIFICACION)
            .tipoIdentificacion(UPDATED_TIPO_IDENTIFICACION)
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .nacionalidad(UPDATED_NACIONALIDAD)
            .edad(UPDATED_EDAD)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .numeroCamisa(UPDATED_NUMERO_CAMISA)
            .contactoEmergencia(UPDATED_CONTACTO_EMERGENCIA)
            .calleAvenidaDireccion(UPDATED_CALLE_AVENIDA_DIRECCION)
            .numeroDireccion(UPDATED_NUMERO_DIRECCION)
            .numeroPersonal(UPDATED_NUMERO_PERSONAL)
            .imagenJugador(UPDATED_IMAGEN_JUGADOR)
            .imagenJugadorContentType(UPDATED_IMAGEN_JUGADOR_CONTENT_TYPE)
            .documentoIdentificacion(UPDATED_DOCUMENTO_IDENTIFICACION)
            .documentoIdentificacionContentType(UPDATED_DOCUMENTO_IDENTIFICACION_CONTENT_TYPE);
        JugadorDTO jugadorDTO = jugadorMapper.toDto(updatedJugador);

        restJugadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jugadorDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Jugador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedJugadorToMatchAllProperties(updatedJugador);
    }

    @Test
    @Transactional
    void putNonExistingJugador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jugador.setId(longCount.incrementAndGet());

        // Create the Jugador
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJugadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jugadorDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jugador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJugador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jugador.setId(longCount.incrementAndGet());

        // Create the Jugador
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJugadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jugadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jugador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJugador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jugador.setId(longCount.incrementAndGet());

        // Create the Jugador
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJugadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jugador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJugadorWithPatch() throws Exception {
        // Initialize the database
        insertedJugador = jugadorRepository.saveAndFlush(jugador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jugador using partial update
        Jugador partialUpdatedJugador = new Jugador();
        partialUpdatedJugador.setId(jugador.getId());

        partialUpdatedJugador
            .idJugador(UPDATED_ID_JUGADOR)
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .nacionalidad(UPDATED_NACIONALIDAD)
            .edad(UPDATED_EDAD)
            .numeroCamisa(UPDATED_NUMERO_CAMISA)
            .calleAvenidaDireccion(UPDATED_CALLE_AVENIDA_DIRECCION)
            .numeroDireccion(UPDATED_NUMERO_DIRECCION)
            .numeroPersonal(UPDATED_NUMERO_PERSONAL)
            .imagenJugador(UPDATED_IMAGEN_JUGADOR)
            .imagenJugadorContentType(UPDATED_IMAGEN_JUGADOR_CONTENT_TYPE);

        restJugadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJugador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJugador))
            )
            .andExpect(status().isOk());

        // Validate the Jugador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJugadorUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedJugador, jugador), getPersistedJugador(jugador));
    }

    @Test
    @Transactional
    void fullUpdateJugadorWithPatch() throws Exception {
        // Initialize the database
        insertedJugador = jugadorRepository.saveAndFlush(jugador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jugador using partial update
        Jugador partialUpdatedJugador = new Jugador();
        partialUpdatedJugador.setId(jugador.getId());

        partialUpdatedJugador
            .idJugador(UPDATED_ID_JUGADOR)
            .nroIdentificacion(UPDATED_NRO_IDENTIFICACION)
            .tipoIdentificacion(UPDATED_TIPO_IDENTIFICACION)
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .nacionalidad(UPDATED_NACIONALIDAD)
            .edad(UPDATED_EDAD)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .numeroCamisa(UPDATED_NUMERO_CAMISA)
            .contactoEmergencia(UPDATED_CONTACTO_EMERGENCIA)
            .calleAvenidaDireccion(UPDATED_CALLE_AVENIDA_DIRECCION)
            .numeroDireccion(UPDATED_NUMERO_DIRECCION)
            .numeroPersonal(UPDATED_NUMERO_PERSONAL)
            .imagenJugador(UPDATED_IMAGEN_JUGADOR)
            .imagenJugadorContentType(UPDATED_IMAGEN_JUGADOR_CONTENT_TYPE)
            .documentoIdentificacion(UPDATED_DOCUMENTO_IDENTIFICACION)
            .documentoIdentificacionContentType(UPDATED_DOCUMENTO_IDENTIFICACION_CONTENT_TYPE);

        restJugadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJugador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJugador))
            )
            .andExpect(status().isOk());

        // Validate the Jugador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJugadorUpdatableFieldsEquals(partialUpdatedJugador, getPersistedJugador(partialUpdatedJugador));
    }

    @Test
    @Transactional
    void patchNonExistingJugador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jugador.setId(longCount.incrementAndGet());

        // Create the Jugador
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJugadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jugadorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jugadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jugador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJugador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jugador.setId(longCount.incrementAndGet());

        // Create the Jugador
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJugadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jugadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jugador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJugador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jugador.setId(longCount.incrementAndGet());

        // Create the Jugador
        JugadorDTO jugadorDTO = jugadorMapper.toDto(jugador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJugadorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(jugadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jugador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJugador() throws Exception {
        // Initialize the database
        insertedJugador = jugadorRepository.saveAndFlush(jugador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the jugador
        restJugadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, jugador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return jugadorRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Jugador getPersistedJugador(Jugador jugador) {
        return jugadorRepository.findById(jugador.getId()).orElseThrow();
    }

    protected void assertPersistedJugadorToMatchAllProperties(Jugador expectedJugador) {
        assertJugadorAllPropertiesEquals(expectedJugador, getPersistedJugador(expectedJugador));
    }

    protected void assertPersistedJugadorToMatchUpdatableProperties(Jugador expectedJugador) {
        assertJugadorAllUpdatablePropertiesEquals(expectedJugador, getPersistedJugador(expectedJugador));
    }
}
