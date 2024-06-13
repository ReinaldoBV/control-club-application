package cl.controlclub.myapp.service.dto;

import cl.controlclub.myapp.domain.enumeration.TipoAsistencia;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Asistencia} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AsistenciaDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoAsistencia tipo;

    @NotNull
    private Long idEvento;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private Boolean asistencia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoAsistencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoAsistencia tipo) {
        this.tipo = tipo;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Boolean asistencia) {
        this.asistencia = asistencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AsistenciaDTO)) {
            return false;
        }

        AsistenciaDTO asistenciaDTO = (AsistenciaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, asistenciaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AsistenciaDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", idEvento=" + getIdEvento() +
            ", fecha='" + getFecha() + "'" +
            ", asistencia='" + getAsistencia() + "'" +
            "}";
    }
}
