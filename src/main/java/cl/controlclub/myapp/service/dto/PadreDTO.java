package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Padre} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PadreDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombres;

    @NotNull
    private String apellidos;

    @NotNull
    private String relacion;

    @NotNull
    private String telefono;

    @NotNull
    private Boolean asociado;

    @NotNull
    private String email;

    private JugadorDTO jugador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getAsociado() {
        return asociado;
    }

    public void setAsociado(Boolean asociado) {
        this.asociado = asociado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JugadorDTO getJugador() {
        return jugador;
    }

    public void setJugador(JugadorDTO jugador) {
        this.jugador = jugador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PadreDTO)) {
            return false;
        }

        PadreDTO padreDTO = (PadreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, padreDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PadreDTO{" +
            "id=" + getId() +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", relacion='" + getRelacion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", asociado='" + getAsociado() + "'" +
            ", email='" + getEmail() + "'" +
            ", jugador=" + getJugador() +
            "}";
    }
}
