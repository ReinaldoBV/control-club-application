package cl.controlclub.myapp.service.dto;

import cl.controlclub.myapp.domain.enumeration.Nacionalidad;
import cl.controlclub.myapp.domain.enumeration.TipoIdentificacion;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Jugador} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JugadorDTO implements Serializable {

    private Long id;

    @NotNull
    private Long nroIdentificacion;

    @NotNull
    private TipoIdentificacion tipoIdentificacion;

    @NotNull
    private String nombres;

    @NotNull
    private String apellidos;

    @NotNull
    private Nacionalidad nacionalidad;

    @NotNull
    private Integer edad;

    @NotNull
    private LocalDate fechaNacimiento;

    @NotNull
    private Integer numeroCamisa;

    @NotNull
    private String contactoEmergencia;

    @NotNull
    private String calleAvenidaDireccion;

    @NotNull
    private Long numeroDireccion;

    @NotNull
    private Long numeroPersonal;

    @Lob
    private byte[] imagenJugador;

    private String imagenJugadorContentType;

    @Lob
    private byte[] documentoIdentificacion;

    private String documentoIdentificacionContentType;

    private CentroSaludDTO centroSalud;

    private PrevisionSaludDTO previsionSalud;

    private CentroEducativoDTO centroEducativo;

    private CategoriasDTO categorias;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNroIdentificacion() {
        return nroIdentificacion;
    }

    public void setNroIdentificacion(Long nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getNumeroCamisa() {
        return numeroCamisa;
    }

    public void setNumeroCamisa(Integer numeroCamisa) {
        this.numeroCamisa = numeroCamisa;
    }

    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public String getCalleAvenidaDireccion() {
        return calleAvenidaDireccion;
    }

    public void setCalleAvenidaDireccion(String calleAvenidaDireccion) {
        this.calleAvenidaDireccion = calleAvenidaDireccion;
    }

    public Long getNumeroDireccion() {
        return numeroDireccion;
    }

    public void setNumeroDireccion(Long numeroDireccion) {
        this.numeroDireccion = numeroDireccion;
    }

    public Long getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(Long numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public byte[] getImagenJugador() {
        return imagenJugador;
    }

    public void setImagenJugador(byte[] imagenJugador) {
        this.imagenJugador = imagenJugador;
    }

    public String getImagenJugadorContentType() {
        return imagenJugadorContentType;
    }

    public void setImagenJugadorContentType(String imagenJugadorContentType) {
        this.imagenJugadorContentType = imagenJugadorContentType;
    }

    public byte[] getDocumentoIdentificacion() {
        return documentoIdentificacion;
    }

    public void setDocumentoIdentificacion(byte[] documentoIdentificacion) {
        this.documentoIdentificacion = documentoIdentificacion;
    }

    public String getDocumentoIdentificacionContentType() {
        return documentoIdentificacionContentType;
    }

    public void setDocumentoIdentificacionContentType(String documentoIdentificacionContentType) {
        this.documentoIdentificacionContentType = documentoIdentificacionContentType;
    }

    public CentroSaludDTO getCentroSalud() {
        return centroSalud;
    }

    public void setCentroSalud(CentroSaludDTO centroSalud) {
        this.centroSalud = centroSalud;
    }

    public PrevisionSaludDTO getPrevisionSalud() {
        return previsionSalud;
    }

    public void setPrevisionSalud(PrevisionSaludDTO previsionSalud) {
        this.previsionSalud = previsionSalud;
    }

    public CentroEducativoDTO getCentroEducativo() {
        return centroEducativo;
    }

    public void setCentroEducativo(CentroEducativoDTO centroEducativo) {
        this.centroEducativo = centroEducativo;
    }

    public CategoriasDTO getCategorias() {
        return categorias;
    }

    public void setCategorias(CategoriasDTO categorias) {
        this.categorias = categorias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JugadorDTO)) {
            return false;
        }

        JugadorDTO jugadorDTO = (JugadorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, jugadorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JugadorDTO{" +
            "id=" + getId() +
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
            ", documentoIdentificacion='" + getDocumentoIdentificacion() + "'" +
            ", centroSalud=" + getCentroSalud() +
            ", previsionSalud=" + getPrevisionSalud() +
            ", centroEducativo=" + getCentroEducativo() +
            ", categorias=" + getCategorias() +
            "}";
    }
}
