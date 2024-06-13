package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Directivos} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DirectivosDTO implements Serializable {

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
    private LocalDate fechaEleccion;

    @NotNull
    private LocalDate fechaVencimiento;

    @NotNull
    private String email;

    private AsociadosDTO asociados;

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

    public LocalDate getFechaEleccion() {
        return fechaEleccion;
    }

    public void setFechaEleccion(LocalDate fechaEleccion) {
        this.fechaEleccion = fechaEleccion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AsociadosDTO getAsociados() {
        return asociados;
    }

    public void setAsociados(AsociadosDTO asociados) {
        this.asociados = asociados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectivosDTO)) {
            return false;
        }

        DirectivosDTO directivosDTO = (DirectivosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, directivosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DirectivosDTO{" +
            "id=" + getId() +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaEleccion='" + getFechaEleccion() + "'" +
            ", fechaVencimiento='" + getFechaVencimiento() + "'" +
            ", email='" + getEmail() + "'" +
            ", asociados=" + getAsociados() +
            "}";
    }
}
