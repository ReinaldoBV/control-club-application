package cl.controlclub.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.TorneosParticipaciones} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TorneosParticipacionesDTO implements Serializable {

    private Long id;

    @NotNull
    private String descripcion;

    @NotNull
    private BigDecimal monto;

    @NotNull
    private LocalDate fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TorneosParticipacionesDTO)) {
            return false;
        }

        TorneosParticipacionesDTO torneosParticipacionesDTO = (TorneosParticipacionesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, torneosParticipacionesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TorneosParticipacionesDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", monto=" + getMonto() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
