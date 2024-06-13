package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Asociados} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AsociadosDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombres;

    @NotNull
    private String apellidos;

    @NotNull
    private String cargo;

    @NotNull
    private String telefono;

    @NotNull
    private LocalDate fechaAsoc;

    @NotNull
    private String email;

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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaAsoc() {
        return fechaAsoc;
    }

    public void setFechaAsoc(LocalDate fechaAsoc) {
        this.fechaAsoc = fechaAsoc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AsociadosDTO)) {
            return false;
        }

        AsociadosDTO asociadosDTO = (AsociadosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, asociadosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AsociadosDTO{" +
            "id=" + getId() +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaAsoc='" + getFechaAsoc() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
