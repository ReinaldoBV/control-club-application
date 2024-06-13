package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.Entrenamiento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EntrenamientoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fechaHora;

    @NotNull
    private Integer duracion;

    private CuerpoTecnicoDTO cuerpoTecnico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public CuerpoTecnicoDTO getCuerpoTecnico() {
        return cuerpoTecnico;
    }

    public void setCuerpoTecnico(CuerpoTecnicoDTO cuerpoTecnico) {
        this.cuerpoTecnico = cuerpoTecnico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntrenamientoDTO)) {
            return false;
        }

        EntrenamientoDTO entrenamientoDTO = (EntrenamientoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entrenamientoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntrenamientoDTO{" +
            "id=" + getId() +
            ", fechaHora='" + getFechaHora() + "'" +
            ", duracion=" + getDuracion() +
            ", cuerpoTecnico=" + getCuerpoTecnico() +
            "}";
    }
}
