package cl.controlclub.myapp.domain;

import cl.controlclub.myapp.domain.enumeration.Nacionalidad;
import cl.controlclub.myapp.domain.enumeration.TipoIdentificacion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Jugador.
 */
@Entity
@Table(name = "jugador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_jugador", nullable = false)
    private Long idJugador;

    @NotNull
    @Column(name = "nro_identificacion", nullable = false)
    private Long nroIdentificacion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_identificacion", nullable = false)
    private TipoIdentificacion tipoIdentificacion;

    @NotNull
    @Column(name = "nombres", nullable = false)
    private String nombres;

    @NotNull
    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nacionalidad", nullable = false)
    private Nacionalidad nacionalidad;

    @NotNull
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotNull
    @Column(name = "numero_camisa", nullable = false)
    private Integer numeroCamisa;

    @NotNull
    @Column(name = "contacto_emergencia", nullable = false)
    private String contactoEmergencia;

    @NotNull
    @Column(name = "calle_avenida_direccion", nullable = false)
    private String calleAvenidaDireccion;

    @NotNull
    @Column(name = "numero_direccion", nullable = false)
    private Long numeroDireccion;

    @NotNull
    @Column(name = "numero_personal", nullable = false)
    private Long numeroPersonal;

    @Lob
    @Column(name = "imagen_jugador")
    private byte[] imagenJugador;

    @Column(name = "imagen_jugador_content_type")
    private String imagenJugadorContentType;

    @Lob
    @Column(name = "documento_identificacion")
    private byte[] documentoIdentificacion;

    @Column(name = "documento_identificacion_content_type")
    private String documentoIdentificacionContentType;

    @JsonIgnoreProperties(value = { "jugador", "asociados", "directivos", "cuerpoTecnico" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "jugador")
    private Usuario usuario;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jugador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "jugador" }, allowSetters = true)
    private Set<Categorias> categorias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Jugador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdJugador() {
        return this.idJugador;
    }

    public Jugador idJugador(Long idJugador) {
        this.setIdJugador(idJugador);
        return this;
    }

    public void setIdJugador(Long idJugador) {
        this.idJugador = idJugador;
    }

    public Long getNroIdentificacion() {
        return this.nroIdentificacion;
    }

    public Jugador nroIdentificacion(Long nroIdentificacion) {
        this.setNroIdentificacion(nroIdentificacion);
        return this;
    }

    public void setNroIdentificacion(Long nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public Jugador tipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.setTipoIdentificacion(tipoIdentificacion);
        return this;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNombres() {
        return this.nombres;
    }

    public Jugador nombres(String nombres) {
        this.setNombres(nombres);
        return this;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public Jugador apellidos(String apellidos) {
        this.setApellidos(apellidos);
        return this;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Nacionalidad getNacionalidad() {
        return this.nacionalidad;
    }

    public Jugador nacionalidad(Nacionalidad nacionalidad) {
        this.setNacionalidad(nacionalidad);
        return this;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Integer getEdad() {
        return this.edad;
    }

    public Jugador edad(Integer edad) {
        this.setEdad(edad);
        return this;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public Jugador fechaNacimiento(LocalDate fechaNacimiento) {
        this.setFechaNacimiento(fechaNacimiento);
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getNumeroCamisa() {
        return this.numeroCamisa;
    }

    public Jugador numeroCamisa(Integer numeroCamisa) {
        this.setNumeroCamisa(numeroCamisa);
        return this;
    }

    public void setNumeroCamisa(Integer numeroCamisa) {
        this.numeroCamisa = numeroCamisa;
    }

    public String getContactoEmergencia() {
        return this.contactoEmergencia;
    }

    public Jugador contactoEmergencia(String contactoEmergencia) {
        this.setContactoEmergencia(contactoEmergencia);
        return this;
    }

    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public String getCalleAvenidaDireccion() {
        return this.calleAvenidaDireccion;
    }

    public Jugador calleAvenidaDireccion(String calleAvenidaDireccion) {
        this.setCalleAvenidaDireccion(calleAvenidaDireccion);
        return this;
    }

    public void setCalleAvenidaDireccion(String calleAvenidaDireccion) {
        this.calleAvenidaDireccion = calleAvenidaDireccion;
    }

    public Long getNumeroDireccion() {
        return this.numeroDireccion;
    }

    public Jugador numeroDireccion(Long numeroDireccion) {
        this.setNumeroDireccion(numeroDireccion);
        return this;
    }

    public void setNumeroDireccion(Long numeroDireccion) {
        this.numeroDireccion = numeroDireccion;
    }

    public Long getNumeroPersonal() {
        return this.numeroPersonal;
    }

    public Jugador numeroPersonal(Long numeroPersonal) {
        this.setNumeroPersonal(numeroPersonal);
        return this;
    }

    public void setNumeroPersonal(Long numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public byte[] getImagenJugador() {
        return this.imagenJugador;
    }

    public Jugador imagenJugador(byte[] imagenJugador) {
        this.setImagenJugador(imagenJugador);
        return this;
    }

    public void setImagenJugador(byte[] imagenJugador) {
        this.imagenJugador = imagenJugador;
    }

    public String getImagenJugadorContentType() {
        return this.imagenJugadorContentType;
    }

    public Jugador imagenJugadorContentType(String imagenJugadorContentType) {
        this.imagenJugadorContentType = imagenJugadorContentType;
        return this;
    }

    public void setImagenJugadorContentType(String imagenJugadorContentType) {
        this.imagenJugadorContentType = imagenJugadorContentType;
    }

    public byte[] getDocumentoIdentificacion() {
        return this.documentoIdentificacion;
    }

    public Jugador documentoIdentificacion(byte[] documentoIdentificacion) {
        this.setDocumentoIdentificacion(documentoIdentificacion);
        return this;
    }

    public void setDocumentoIdentificacion(byte[] documentoIdentificacion) {
        this.documentoIdentificacion = documentoIdentificacion;
    }

    public String getDocumentoIdentificacionContentType() {
        return this.documentoIdentificacionContentType;
    }

    public Jugador documentoIdentificacionContentType(String documentoIdentificacionContentType) {
        this.documentoIdentificacionContentType = documentoIdentificacionContentType;
        return this;
    }

    public void setDocumentoIdentificacionContentType(String documentoIdentificacionContentType) {
        this.documentoIdentificacionContentType = documentoIdentificacionContentType;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (this.usuario != null) {
            this.usuario.setJugador(null);
        }
        if (usuario != null) {
            usuario.setJugador(this);
        }
        this.usuario = usuario;
    }

    public Jugador usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public Set<Categorias> getCategorias() {
        return this.categorias;
    }

    public void setCategorias(Set<Categorias> categorias) {
        if (this.categorias != null) {
            this.categorias.forEach(i -> i.setJugador(null));
        }
        if (categorias != null) {
            categorias.forEach(i -> i.setJugador(this));
        }
        this.categorias = categorias;
    }

    public Jugador categorias(Set<Categorias> categorias) {
        this.setCategorias(categorias);
        return this;
    }

    public Jugador addCategorias(Categorias categorias) {
        this.categorias.add(categorias);
        categorias.setJugador(this);
        return this;
    }

    public Jugador removeCategorias(Categorias categorias) {
        this.categorias.remove(categorias);
        categorias.setJugador(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jugador)) {
            return false;
        }
        return getId() != null && getId().equals(((Jugador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jugador{" +
            "id=" + getId() +
            ", idJugador=" + getIdJugador() +
            ", nroIdentificacion=" + getNroIdentificacion() +
            ", tipoIdentificacion='" + getTipoIdentificacion() + "'" +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", nacionalidad='" + getNacionalidad() + "'" +
            ", edad=" + getEdad() +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", numeroCamisa=" + getNumeroCamisa() +
            ", contactoEmergencia='" + getContactoEmergencia() + "'" +
            ", calleAvenidaDireccion='" + getCalleAvenidaDireccion() + "'" +
            ", numeroDireccion=" + getNumeroDireccion() +
            ", numeroPersonal=" + getNumeroPersonal() +
            ", imagenJugador='" + getImagenJugador() + "'" +
            ", imagenJugadorContentType='" + getImagenJugadorContentType() + "'" +
            ", documentoIdentificacion='" + getDocumentoIdentificacion() + "'" +
            ", documentoIdentificacionContentType='" + getDocumentoIdentificacionContentType() + "'" +
            "}";
    }
}
