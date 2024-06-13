package cl.controlclub.myapp.service.dto;

import cl.controlclub.myapp.domain.enumeration.TipoIngreso;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link cl.controlclub.myapp.domain.FinanzasIngreso} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FinanzasIngresoDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoIngreso tipo;

    @NotNull
    private String descripcion;

    @NotNull
    private BigDecimal monto;

    @NotNull
    private LocalDate fecha;

    private JugadorDTO jugador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoIngreso getTipo() {
        return tipo;
    }

    public void setTipo(TipoIngreso tipo) {
        this.tipo = tipo;
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
        if (!(o instanceof FinanzasIngresoDTO)) {
            return false;
        }

        FinanzasIngresoDTO finanzasIngresoDTO = (FinanzasIngresoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, finanzasIngresoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FinanzasIngresoDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", monto=" + getMonto() +
            ", fecha='" + getFecha() + "'" +
            ", jugador=" + getJugador() +
            "}";
    }
}
