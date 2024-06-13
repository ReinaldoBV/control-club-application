package cl.controlclub.myapp.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.EstadisticasBaloncesto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadisticasBaloncestoDTO implements Serializable {

    private Long id;

    private Integer puntos;

    private Integer rebotes;

    private Integer asistencias;

    private Integer robos;

    private Integer bloqueos;

    private BigDecimal porcentajeTiro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getRebotes() {
        return rebotes;
    }

    public void setRebotes(Integer rebotes) {
        this.rebotes = rebotes;
    }

    public Integer getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(Integer asistencias) {
        this.asistencias = asistencias;
    }

    public Integer getRobos() {
        return robos;
    }

    public void setRobos(Integer robos) {
        this.robos = robos;
    }

    public Integer getBloqueos() {
        return bloqueos;
    }

    public void setBloqueos(Integer bloqueos) {
        this.bloqueos = bloqueos;
    }

    public BigDecimal getPorcentajeTiro() {
        return porcentajeTiro;
    }

    public void setPorcentajeTiro(BigDecimal porcentajeTiro) {
        this.porcentajeTiro = porcentajeTiro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadisticasBaloncestoDTO)) {
            return false;
        }

        EstadisticasBaloncestoDTO estadisticasBaloncestoDTO = (EstadisticasBaloncestoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estadisticasBaloncestoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadisticasBaloncestoDTO{" +
            "id=" + getId() +
            ", puntos=" + getPuntos() +
            ", rebotes=" + getRebotes() +
            ", asistencias=" + getAsistencias() +
            ", robos=" + getRobos() +
            ", bloqueos=" + getBloqueos() +
            ", porcentajeTiro=" + getPorcentajeTiro() +
            "}";
    }
}
