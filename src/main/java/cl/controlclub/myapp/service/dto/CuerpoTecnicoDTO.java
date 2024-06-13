package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.CuerpoTecnico} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CuerpoTecnicoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombres;

    @NotNull
    private String apellidos;

    @NotNull
    private String rolTecnico;

    @NotNull
    private String telefono;

    @NotNull
    private LocalDate fechaInicio;

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

    public String getRolTecnico() {
        return rolTecnico;
    }

    public void setRolTecnico(String rolTecnico) {
        this.rolTecnico = rolTecnico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
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
        if (!(o instanceof CuerpoTecnicoDTO)) {
            return false;
        }

        CuerpoTecnicoDTO cuerpoTecnicoDTO = (CuerpoTecnicoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cuerpoTecnicoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CuerpoTecnicoDTO{" +
            "id=" + getId() +
            ", nombres='" + getNombres() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", rolTecnico='" + getRolTecnico() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", email='" + getEmail() + "'" +
            ", asociados=" + getAsociados() +
            "}";
    }
}
